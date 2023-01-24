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

package org.taxman.h6.oldsearch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskHelper extends Thread {

    private static final int MODE_IDLE = 0;
    private static final int MODE_WRITE_TO_DISK = 1;
    private static final int MODE_LOAD_FROM_DISK = 2;
    private static final int MODE_READY_TO_STREAM = 3;
    private static final int MODE_TIME_TO_SHUT_DOWN = 4;
    private static final int MODE_ERR = 5;
    private static final int MAX_MAILBOX_SIZE = 10;

    private Path tmpFile = null;
    private volatile List<List<TaskData>> mailbox;
    private int tasksOnDisk = 0;

    private volatile int mode = MODE_IDLE;


    public DiskHelper(int game, int name) {
        super();
        this.setName(game + "_" + name);
        this.mailbox = new ArrayList<>();
    }

    // called by task threads when a search queue buffer is full
    public void sendToDisk(List<TaskData> taskDataList) {
        //assert mode == MODE_WRITE_TO_DISK || mode == MODE_IDLE: "mode should not be " + mode;
        assert mode <= 3 : "bad mode: " + mode;
        synchronized (this) {
            mailbox.add(taskDataList);
            if (mode == MODE_IDLE && mailbox.size() > MAX_MAILBOX_SIZE) {
                mode = MODE_WRITE_TO_DISK;
                start();
            } else {
                notify();
            }
        }
    }

    // called by main thread when we're done with a target or done with the whole search
    public void shutdown() {
        synchronized (this) {
            if (mode != MODE_IDLE) {
                mode = MODE_TIME_TO_SHUT_DOWN;
                this.interrupt();
            }
        }
    }

    // called by main thread when it will be time to stream soon
    public void switchToLoading() {
        synchronized (this) {
            assert mode == MODE_WRITE_TO_DISK || mode == MODE_IDLE: "error, mode is " + mode;
            if (mode != MODE_IDLE) {
                mode = MODE_LOAD_FROM_DISK;
                notify();
            }
        }
    }

    // called by main thread
    public Stream<TaskData> stream() {
        synchronized (this) {
            switch (mode) {
                case MODE_READY_TO_STREAM:
                case MODE_IDLE:
                    break; // do nothing
                case MODE_WRITE_TO_DISK:
                    switchToLoading();
                case MODE_LOAD_FROM_DISK:
                    try {
                        while (mode != MODE_READY_TO_STREAM) this.wait();
                    } catch (InterruptedException ie) {
                        // exit loop
                    }
                    break;
                case MODE_TIME_TO_SHUT_DOWN:
                case MODE_ERR:
                default:
                    throw new RuntimeException("mode " + mode + ": an unexpected error occurred");
            }
        }

        return mailbox.stream()
                .flatMap(List::stream);
    }

    public void run() {
        try {
            var filePrefix = "tx" + getName() + "_";
            tmpFile = Files.createTempFile(filePrefix, ".tmp");

            while (mode == MODE_WRITE_TO_DISK) {
                if (mailbox.size() > MAX_MAILBOX_SIZE) mailboxToFile();
                synchronized (this) {
                    if (mailbox.size() <= MAX_MAILBOX_SIZE && mode == MODE_WRITE_TO_DISK) wait();
                }
            }

            if (mode == MODE_LOAD_FROM_DISK) {
                loadFromDisk();
            }

            synchronized (this) {
                while (mode != MODE_TIME_TO_SHUT_DOWN) wait();
            }

        } catch (InterruptedException e) {
            // time to be done
        } catch (Exception e) {
            e.printStackTrace();
            synchronized (this) {
                mode = MODE_ERR;
            }
        }
        doShutdown();
    }

    private void mailboxToFile() {
        List<List<TaskData>> toOutput;
        synchronized (this) {
            toOutput = mailbox;
            mailbox = new ArrayList<>();
        }
        //System.out.printf("      writing to %s\n", tmpFile);
        try (var outputStream = new BufferedOutputStream(Files.newOutputStream(tmpFile, StandardOpenOption.APPEND))) {
            toOutput.stream()
                    .flatMap(List<TaskData>::stream)
                    .forEach(td -> write(td, outputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(TaskData td, OutputStream outputStream) {
        var bytes = td.toByteArray();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            System.out.printf("ERROR writing %,d bytes of task data to file %s\n", bytes.length, tmpFile);
            File f = tmpFile.toFile();
            System.out.printf("    %,d total space, %,d free space, %,d usable space\n",
                    f.getTotalSpace(), f.getFreeSpace(), f.getUsableSpace());
            throw new RuntimeException(e);
        }
        ++tasksOnDisk;
    }

    private void loadFromDisk() {
        try {
            //System.out.printf("      reading from %s\n", tmpFile);
            try (var is = new BufferedInputStream(Files.newInputStream(tmpFile))) {
                var fromDisk = Stream.generate(() -> null)
                        .map(nada -> fromInputStream(is))
                        .takeWhile(Objects::nonNull)
                        .collect(Collectors.toList());
                synchronized (this) {
                    mailbox.add(fromDisk);
                    mode = MODE_READY_TO_STREAM;
                }
            }
            synchronized (this) {
                Files.delete(tmpFile);
                tmpFile = null;
                tasksOnDisk = 0;
                notify();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TaskData fromInputStream(BufferedInputStream is) {
        try {
            return TaskData.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doShutdown() {
        mailbox = new ArrayList<>();

        try {
            if (tmpFile != null) {
                Files.delete(tmpFile);
                tmpFile = null;
                tasksOnDisk = 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getCountOfTasksInMemory() {
        synchronized (this) {
            return mailbox.stream()
                   .mapToInt(List::size)
                   .sum();
        }
    }

    public long getTaskCountOnDisk() {
        synchronized (this) {
            return tasksOnDisk;
        }
    }
}
