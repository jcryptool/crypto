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

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

// All the code required to set the priority of the worker threads a little lower.
public class TxWorkerThread extends ForkJoinWorkerThread {
    private final static int PRIORITY = Thread.NORM_PRIORITY - 1;
    private final static ForkJoinPool.ForkJoinWorkerThreadFactory factory = TxWorkerThread::new;
    public static int maxThreads = Runtime.getRuntime().availableProcessors() - 1;  // set this to 1 for debugging
    private static ForkJoinPool pool = null;

    public static ForkJoinPool getWorkerPool() {
        if (pool == null) {
            pool = new ForkJoinPool(maxThreads, factory, null, false);
        }
        return pool;
    }

    public TxWorkerThread(ForkJoinPool pool) {
        super(pool);
        this.setPriority(PRIORITY);
    }

}
