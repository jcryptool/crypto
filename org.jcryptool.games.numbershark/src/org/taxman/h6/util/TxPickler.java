/*
 * Copyright (c) Brian Chess 2019-2023.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.taxman.h6.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Have a list that's uncomfortably large for keeping in memory?  Use this class to drop it to disk.
 * Streaming or otherwise reading the list back will delete the underlying temp file.  Uses a daemon
 * thread to avoid slowing down adding elements to the list.
 */
public abstract class TxPickler<T> {

    public abstract void toStream(T t, DataOutputStream out) throws IOException;
    public abstract T fromStream(DataInputStream in) throws IOException;

    public static final int DEFAULT_BUFFER_SIZE = 1000;
    private static final List<TxPickler<?>> dblsThatNeedToFlush = new ArrayList<>();

    private final int maxBufferSize;
    private volatile boolean streamMode;
    private volatile boolean hasBeenShutDown;
    private volatile boolean needsToFlush;
    private Path path;
    private List<T> inbox;  // stuff coming in from add()
    private long numWritten;
    private DataOutputStream out;

    public TxPickler() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public TxPickler(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
        this.streamMode = false;
        this.hasBeenShutDown = false;
        this.needsToFlush = false;
        this.path = null;
        this.numWritten = 0;
        this.inbox = new ArrayList<>(DEFAULT_BUFFER_SIZE);
        this.out = null;
    }

    public void add(T t) {
        assert !hasBeenShutDown;
        synchronized (inbox) {
            inbox.add(t);
            manageInboxSize();
        }
    }

    public void addAll(Collection<T> list) {
        assert !hasBeenShutDown;
        synchronized (inbox) {
            inbox.addAll(list);
            manageInboxSize();
        }
    }

    private void manageInboxSize() {
        if (inbox.size() >= maxBufferSize && !streamMode && !hasBeenShutDown && !needsToFlush) {
            synchronized (dblsThatNeedToFlush) {
                if (!needsToFlush) {
                    needsToFlush = true;
                    dblsThatNeedToFlush.add(this);
                    dblsThatNeedToFlush.notify();
                }
            }
        }
    }

    private synchronized void flushToDisk() {
        if (streamMode || hasBeenShutDown) return;  // too late to go to disk

        List<T> flushMe;
        //noinspection SynchronizeOnNonFinalField
        synchronized (inbox) {
            flushMe = inbox;
            inbox = new ArrayList<>(DEFAULT_BUFFER_SIZE);
            needsToFlush = false;
        }

        if (path == null || out == null) {
            try {
                path = Files.createTempFile("tx_", ".tmp");
                var fos = Files.newOutputStream(path, StandardOpenOption.APPEND);
                var gos = new GZIPOutputStream(fos);
                var bos = new BufferedOutputStream(gos,65536);
                out = new DataOutputStream(bos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        flushMe.forEach(this::write);
    }

    private void write(T t) {
        try {
            toStream(t, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ++numWritten;
    }

    public long size() {
        return numWritten + inboxSize();
    }

    public long sizeOnDisk() {
        return numWritten;
    }

    public long inboxSize() {
        return inbox.size();
    }

    public synchronized Stream<T> stream() {
        if (streamMode) throw new RuntimeException("disk backed list is already streaming");
        streamMode = true;
        if (out == null) return inbox.stream();

        DataInputStream in;
        try {
            out.close();
            out = null;
            var fis = Files.newInputStream(path);
            var gis = new GZIPInputStream(fis);
            var bis = new BufferedInputStream(gis, 65536);
            in = new DataInputStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var iter = new Iterator<T>() {
            private T next;
            @Override
            public boolean hasNext() {
                var result = true;
                if (hasBeenShutDown) {
                    next = null;
                    result = false;
                } else {
                    try {
                        next = fromStream(in);
                    } catch (EOFException eof) {
                        result = false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        if (!result) deleteFile();
                    }
                }
                return result;
            }

            @Override
            public T next() {
                return next;
            }
        };

        var splt = Spliterators.spliteratorUnknownSize(iter, Spliterator.IMMUTABLE);
        var diskStream = StreamSupport.stream(splt, false);
        return Stream.concat(diskStream, inbox.stream());
    }

    public List<T> readAll() {
        return stream().collect(Collectors.toList());
    }

    private synchronized void deleteFile() {
        try {
            if (out != null) {
                out.close();
                out = null;
            }
            if (path != null) {
                Files.delete(path);
                path = null;
                // don't reset stream mode in case the daemon is still going to try flushing to disk
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        //System.out.println("shutting down " + name);
        hasBeenShutDown = true;
        //noinspection SynchronizeOnNonFinalField
        synchronized (inbox) {
            inbox.clear();
        }
        deleteFile();
    }

    private static final Thread diskBuddy = new Thread(() -> {
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                List<TxPickler<?>> toFlush;
                synchronized (dblsThatNeedToFlush) {
                    while (dblsThatNeedToFlush.size() == 0) dblsThatNeedToFlush.wait();
                    toFlush = new ArrayList<>(dblsThatNeedToFlush);
                    dblsThatNeedToFlush.clear();
                }
                toFlush.stream().parallel().forEach(TxPickler::flushToDisk);
                toFlush.clear();
            } catch (InterruptedException e) {
                keepRunning = false;
            } catch (Exception e) {
                System.out.println("unexpected exception in " + Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
    });

    static {
        diskBuddy.setName("taxman flush to disk daemon");
        diskBuddy.setDaemon(true);
        diskBuddy.start();
    }
}