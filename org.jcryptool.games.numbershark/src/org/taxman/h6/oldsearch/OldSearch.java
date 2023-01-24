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

import org.taxman.h6.util.Stopwatch;
import org.taxman.h6.util.TxPredicate;
import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxWorkerThread;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;

public class OldSearch {
    public static boolean printStatsPerTarget = false;
    public static boolean printSummary = false;
    public static boolean printSearchQueueStats = false;


    private final TxSet candidateNumbers;
    private final TxPredicate<TxSet> predicate;
    private final int searchMaxSize;
    private final int searchMaxSum;
    private final int searchMinSum;
    private final SearchQueueManager sqm;

    private final ForkJoinPool pool;
    private AtomicLong taskCounter;
    private int currentTopLevelTarget;
    private volatile boolean finished = false;
    private boolean forkTasks = true;


    public OldSearch(TxSet candidateNumbers, TxPredicate<TxSet> predicate, int maxSize, int maxSum, int minSum, int game) {
        this.candidateNumbers = candidateNumbers;
        this.predicate = predicate;
        this.searchMaxSize = maxSize;
        this.searchMaxSum = maxSum;
        this.searchMinSum = minSum;
        this.sqm = new SearchQueueManager(maxSum, minSum, game);
        this.pool = TxWorkerThread.getWorkerPool();
    }

    public static TxSet findLargest(TxSet numbers, int maxSize, int maxSum, int minSum, int game,
                                    TxPredicate<TxSet> predicate) {
        return new OldSearch(numbers, predicate, maxSize, maxSum, minSum, game).findLargest();
    }

    private TxSet findLargest() {
        try {
            long taskTotal = 0;
            int levelsExplored = 0;
            var stopwatch = new Stopwatch().start();

            TxSet result = null;
            for (currentTopLevelTarget = searchMaxSum; result == null; currentTopLevelTarget--) {
                taskCounter = new AtomicLong(0);
                result = findTarget(currentTopLevelTarget);
                taskTotal += taskCounter.get();
                ++levelsExplored;
            }
            stopwatch.stop();

            if (printSummary) {
                System.out.printf("  explored %d levels with %,d tasks in %,.1f seconds\n",
                        levelsExplored, taskTotal, stopwatch.seconds());
                System.out.printf("  parallelism: %d\n", pool.getParallelism());
            }

            return result;
        }
        finally {
            sqm.shutdownAll();
        }
    }

    private TxSet findTarget(int target) {
        Stopwatch stopwatch = (printStatsPerTarget) ? new Stopwatch().start() : null;

        List<TargetFinder> targetFinders;
        try (var strm=streamTargetFinders(target)) {
            targetFinders = strm.map(this::submit).collect(Collectors.toList());
        }

        var result = targetFinders.stream()
                .map(ForkJoinTask::join)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (result != null) finished = true;  // this will cause any tasks that are still running to finish quickly

        forkTasks = targetFinders.size() < 10000; // setting up for the next round

        if (stopwatch != null) {
            stopwatch.stop();
            System.out.printf("    searching for %d took %,.2f seconds with %,d starting points and %,d tasks\n",
                    target, stopwatch.seconds(), targetFinders.size(), taskCounter.get());
        }
        if (printSearchQueueStats) sqm.statusReport(System.out);
        return result;
    }

    private TargetFinder submit(TargetFinder tf) {
        try {
            pool.submit(tf);
        } catch (RejectedExecutionException e) {
            throw new RuntimeException("oops", e);
        }
        return tf;
    }

    private Stream<TargetFinder> streamTargetFinders(int target) {
        Stream<TargetFinder> result;
        if (target == searchMaxSum) {
            result = Stream.of(new TargetFinder(candidateNumbers, EmptySet, target));
        } else {
            result = sqm.stream(target)
                    .map(TargetFinder::new);
        }
        return result;
    }

    private class TargetFinder extends RecursiveTask<TxSet> {
        private final TxSet candidates;
        private final TxSet base;
        private final int target;

        private TargetFinder(TxSet candidates, TxSet base, int target) {
            assert target >= 0 : "target must be greater than or equal to zero";
            this.candidates = candidates;
            this.base = base;
            this.target = target;
        }

        private TargetFinder(TaskData data) {
            this(data.candidates, data.base, data.target);
        }

        private void wakeAt(TxSet candidates, TxSet base, int target, int maxPotential) {
            if (maxPotential < 0) maxPotential = 0;
            int wakeAt = currentTopLevelTarget - target + maxPotential;

            if (wakeAt >= searchMinSum) {
                //System.out.printf("waking at %d: %d=%s\n", wakeAt, base.sum(), base);
                sqm.add(wakeAt, new TaskData(candidates, base, maxPotential, EmptySet));
            }
        }

        @Override
        protected TxSet compute() {
            if (finished) return null;
            taskCounter.incrementAndGet();

            //System.out.printf("      base: %s, target %d, maxSize: %d\n", base, target, maxSize);

            TxSet result;
            if (target == 0) {
                result = base;
            } else {
                wakeAt(EmptySet, base, target, 0);
                result = recurse();
            }
            return result;
        }

        private TxSet recurse() {
            var finders = createBranches();
            Stream<TxSet> s;
            if (forkTasks) {
                // early on we submit back to the ForkJoin pool to get plenty of parallelism
                s = invokeAll(finders).stream()
                        .map(ForkJoinTask::join);
            } else {
                // for less ForkJoin overhead, compute recursively
                s = finders.stream()
                        .map(TargetFinder::compute);
            }
            return s.filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }


        private List<TargetFinder> createBranches() {
            List<TargetFinder> result = new ArrayList<>();
            int maxSize = searchMaxSize - base.size();

            int countThatMightWork=0;
            var newCandidates = TxSet.of(candidates);

            for (int n: newCandidates.descendingArray()) {
                if (n > target || !predicate.test(TxSet.or(base, n))) {
                    newCandidates.remove(n);
                } else {
                    if (++countThatMightWork == maxSize) break;
                }
            }


            //System.out.printf("1target: %d, maxSize: %d, base: %s, new candidates: %s\n", target, maxSize, base, newCandidates);


            for (int n: newCandidates.descendingArray()) {
                int sum = newCandidates.sumOfLargest(maxSize);

                if (sum < target) {
                    wakeAt(TxSet.of(newCandidates), base, target, sum);
                    break;
                }

                newCandidates.remove(n);
                int newTarget = target - n;
                TxSet newBase = TxSet.or(base, n);
                if (predicate.test(newBase)) {
                    result.add(new TargetFinder(TxSet.of(newCandidates), newBase, newTarget));
                }
            }
            return result;
        }

    }

}