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

import org.taxman.h6.frame.FrameTestResult;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class Memo {
    public static boolean trackHits = false;
    public static int sizeLimitMillions = 1;

    private MemoMap[] maps = new MemoMap[10];
    private volatile boolean writeNewEntries = true;
    private final String name;


    public Memo(String name, TxSet vocabulary) {
        this.name = name;
    }

    public FrameTestResult test(TxSet set, Supplier<FrameTestResult> compute) {
        //if (true) return compute.get();  // this bypasses memoization
        var map = getOrMakeMap(set);
        //set = compressor.compress(set);  // todo: compression is cheap but not free.  Maybe use only if cache size is huge?
        var result = map.getOrDefault(set.bits, null);
        if (result == null) {
            //if (cleanClock.incrementAndGet() == DECISION_POINT) decideOnFurtherWrites();
            result =  compute.get();
            if (writeNewEntries) map.put(set.bits, compute.get());
        }
        return result;
    }

    private void decideOnFurtherWrites() {
        writeNewEntries = percentOfEntriesThatHaveBeenHit() > 1.0;
        if (!writeNewEntries) {
            System.out.printf("shutting down writes for %s\n", name);
        }
    }

    private MemoMap getOrMakeMap(TxSet set) {
        int idx = set.sum();
        if (idx >= maps.length) {
            synchronized (this) {
                if (idx >= maps.length) {
                    var newMaps = new MemoMap[idx*2];
                    System.arraycopy(maps, 0, newMaps, 0, maps.length);
                    maps = newMaps;
                }
            }
        }
        if (maps[idx] == null) {
            synchronized (this) {
                if (maps[idx] == null) {
                    maps[idx] = new MemoMap(name + "_" + idx);
                }
            }
        }
        return maps[idx];
    }

    public int size() {
        return Arrays.stream(maps)
                .filter(Objects::nonNull)
                .mapToInt(MemoMap::size)
                .sum();
    }

    public long lookupCount() {
        return Arrays.stream(maps)
                .filter(Objects::nonNull)
                .mapToLong(MemoMap::lookupCount)
                .sum();
    }

    public long hitCount() {
        return Arrays.stream(maps)
                .filter(Objects::nonNull)
                .mapToLong(MemoMap::hitCount)
                .sum();
    }

    public long numWithHits() {
        return Arrays.stream(maps)
                .filter(Objects::nonNull)
                .mapToLong(MemoMap::numWithHits)
                .sum();
    }

    public double percentOfEntriesThatHaveBeenHit() {
        return 100.0 * numWithHits() / size();
    }

    public String getCacheStats() {
        if (trackHits) {
            var numMaps = Arrays.stream(maps)
                    .filter(Objects::nonNull)
                    .count();
            var numWithHits = Arrays.stream(maps)
                    .filter(Objects::nonNull)
                    .mapToLong(MemoMap::numWithHits)
                    .count();
            var lookups = lookupCount();
            var hits = hitCount();
            var hitRate = (lookups != 0) ? hits * 100.0 / lookups : 0;
            var hitPercent = numWithHits * 100.0 / size();
            var fmt = "  memoization cache %s:" +
                      " hit rate: %.2f%%, percent hit: %.2f%%  size: %,d, lookups: %,d, tables: %,d \n";
            return String.format(fmt, this.name, hitRate, hitPercent, size(), lookups, numMaps);
        } else {
            return String.format("  hit rate for memoization cache %s not tracked\n", this.name);
        }
    }

    private static class MemoEntry {
        private final FrameTestResult value;
        private volatile int hitCounter;

        private MemoEntry(FrameTestResult value) {
            this.value = value;
            hitCounter = 0;
        }

        public synchronized FrameTestResult get() {
            ++hitCounter;
            return value;
        }

        public int hitCount() {
            return hitCounter;
        }

        public synchronized void decrementHits() {
            --hitCounter;
        }
    }

    private static class MemoMap {
        private final String name;
        private final ConcurrentHashMap<BitSet, MemoEntry> map = new ConcurrentHashMap<>();
        private final AtomicLong missCounter = new AtomicLong(0);
        private final AtomicLong hitCounter = new AtomicLong(0);

        public MemoMap(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public void put(BitSet key, FrameTestResult value) {
            map.put(key, new MemoEntry(value));
        }

        public FrameTestResult getOrDefault(BitSet key, FrameTestResult dflt) {
            var entry = map.getOrDefault(key, null);
            FrameTestResult result;
            if (entry != null) {
                result = entry.value;
                if (trackHits) hitCounter.incrementAndGet();
            } else {
                result = dflt;
                if (trackHits) missCounter.incrementAndGet();
            }
            return result;
        }

        private void clean() {
            map.forEach((key, memoEntry) -> {
                if (memoEntry.hitCount() == 0) {
                    map.remove(key);
                } else {
                    memoEntry.decrementHits();
                }
            });
        }

        public int size() {
            return map.size();
        }

        public long numWithHits() {
            return map.values().stream()
                    .filter(e->e.hitCounter > 0)
                    .count();
        }

        public double percentWithHits() {
            //System.out.printf("  percent with hits for %s (size %d) is %.1f\n", name, size(), 100.0 * numWithHits() / size());
            return 100.0 * numWithHits() / size();
        }

        public boolean lowHitPercent() {
            return percentWithHits() < 1.0;
        }

        public long lookupCount() {
            return hitCounter.get() + missCounter.get();
        }

        public long hitCount() {
            return hitCounter.get() ;
        }
    }

}