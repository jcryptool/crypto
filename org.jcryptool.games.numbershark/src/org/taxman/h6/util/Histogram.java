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

import java.io.PrintStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Histogram<K extends Comparable<K>> {
    private final ConcurrentHashMap<K, AtomicLong> map = new ConcurrentHashMap<>();

    public Histogram() {
    }

    public void add(K bucket) {
        map.computeIfAbsent(bucket, x -> new AtomicLong(0)).incrementAndGet();
    }

    public void display(PrintStream ps, double percentThreshold) {
        long total = map.values().stream()
                .mapToLong(AtomicLong::get)
                .sum();
        var runningTotal = new AtomicLong(0);

        map.keySet().stream()
                .sorted()
                .forEach(k -> {
                    long val = map.get(k).get();
                    double pct = (double) 100 * val / total;
                    runningTotal.addAndGet(val);
                    double runningTotalPct = (double) 100 * runningTotal.get() / total;
                    if (pct > percentThreshold) ps.printf("%s: %,d, %2.0f%%, %2.0f%%\n", k, val, pct, runningTotalPct);
                });
    }
}
