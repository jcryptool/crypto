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

package org.taxman.h6.search;

import org.taxman.h6.frame.BaseFrame;
import org.taxman.h6.game.Board;
import org.taxman.h6.util.Stopwatch;
import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import java.lang.ref.SoftReference;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;

public class SearchManager {
    final FrameSet startingPoint;
    final FactorProvider math;
    final UpperBoundEstimator ube;
    private final ForkJoinPool pool;
    private final TxUnmodifiableSet allPromotionCandidates;

    volatile TxUnmodifiableSet bestSolution;
    volatile int bestSolutionSum;
    final int maxTotalPromotions;

    private volatile Throwable branchException;
    final AtomicLong branchCounter;
    final StatusReportState statusReporter;

    static final long STATUS_REPORT_FREQUENCY_IN_BRANCHES = 1000000;  // call for a report at this frequency
    private static final long STATUS_REPORT_FREQUENCY_IN_SECONDS = 60;  // only print report at this interval

    public static boolean debugPrintSummary = false;
    public static boolean debugPrintDetail = false;
    public static boolean debugPrintFineGrainDetail = false;

    private final String debugPrefix;


    public static void resetDebugFlags() {
        debugPrintSummary = false;
        debugPrintDetail = false;
        debugPrintFineGrainDetail = false;
    }

    private SearchManager(FrameSet startingPoint,
                          TxUnmodifiableSet allPromotionCandidates, UpperBoundEstimator ube,
                          TxUnmodifiableSet initialBestSolution, int maxTotalPromotions, String debugPrefix) {
        this.startingPoint = startingPoint;
        this.math = startingPoint.math;
        this.allPromotionCandidates = allPromotionCandidates;
        this.pool = ForkJoinPool.commonPool();
        this.ube = ube;
        this.bestSolution = initialBestSolution;
        this.bestSolutionSum = initialBestSolution.sum();
        this.branchCounter = new AtomicLong(1);
        this.statusReporter = new StatusReportState();
        this.maxTotalPromotions = maxTotalPromotions;
        this.debugPrefix = debugPrefix;
    }

    public static SearchManager create(Board board, BaseFrame oldSchool) {
        var candidates = oldSchool.allCandidateNumbersIncludingDownstream().unmodifiable();
        List<BaseFrame> oldSchoolFrames = oldSchool.getAllFrames();
        List<TxUnmodifiableSet> columns = new ArrayList<>();
        oldSchoolFrames.forEach(osf -> {
            columns.add(osf.remainingFactors().unmodifiable());
            columns.add(osf.remainingSources().unmodifiable());
        });

        return create(board, columns, candidates);
    }

    static SearchManager create(Board board, List<TxUnmodifiableSet> columns, TxUnmodifiableSet candidates) {
        TxSet allNumbers = TxSet.of(columns.stream().flatMapToInt(TxSet::stream));
        FactorProvider math = FactorProvider.create(board, allNumbers);
        var startingPoint = FrameSet.create(math, columns);
        return create(startingPoint, candidates, "  ");
    }

    /**
     * Creates a search manager and sets the initial best solution to be the result of a quick greedy search.
     * Also takes a close look at the maximum number of promotions to set a tight bound.  This might result
     * in a search of its own, so this can be a time-consuming operation.
     */
    static SearchManager create(FrameSet startingPoint, TxUnmodifiableSet candidates, String debugPrefix) {
        var memoizedStartingPoint = startingPoint.memoize();
        var ube = UpperBoundEstimator.create(candidates, memoizedStartingPoint);
        // come up with an initial solution.  Could try harder here, but eventually it seemed that
        // doing something fast was more important than getting the highest possible score
        var greedySolution =
                LowerBoundEstimator.greedy(memoizedStartingPoint, candidates, debugPrefix);
        var revisedStartingPoint = ube.reviseFreedoms(startingPoint, candidates, greedySolution);
        int maxTotalPromotions = revisedStartingPoint.computeMaxNumberOfPromotions(candidates);

        // Can't have search manager hang on to memoized starting point, or it will keep hold onto an entire
        // search tree of memoized frames.  We have to start over with memoization as part of the search.
        return new SearchManager(revisedStartingPoint, candidates, ube, greedySolution, maxTotalPromotions, debugPrefix);
    }

    public static TxUnmodifiableSet findLargest(Board board, BaseFrame frame) {
        return create(board, frame).findOptimalPromotions();
    }

    TxUnmodifiableSet findOptimalPromotions() {

        var stopwatch = new Stopwatch().start();
        try {
            pool.invoke(
                    new BranchExplorer(
                            new Branch(startingPoint.memoize(), EmptySet, allPromotionCandidates),
                            this
                    )
            );
        }
        finally {
            stopwatch.stop();
            if (debugPrintSummary && branchException == null) {
                finalReport(stopwatch.seconds());
            }
        }
        if (branchException != null) {
            throw new RuntimeException("exception in branch", branchException);
        }
        return bestSolution;
    }

    void reportBranchException(Throwable t) {
        branchException = t;
    }

    public long getTaskCount() {
        return startingPoint.candidateMapper.promotionCounter.get();
    }

    public void increaseSearchMinimum(TxSet challenger) {
        var newBest = challenger.unmodifiable();
        var newBestSum = newBest.sum();

        if (newBestSum > bestSolutionSum) {
            synchronized (this) {
                if (newBestSum > bestSolutionSum) {
                    if (SearchManager.debugPrintDetail) {
                        debug("      increasing search minimum to %d with %d promotions\n",
                                newBestSum, newBest.size());
                    }
                    bestSolutionSum = newBestSum;
                    bestSolution = newBest;
                }
            }
        }
    }

    private void finalReport(float seconds) {
        var fmt = "%,d branches and %,d tasks in %,.1f seconds (parallelism: %d)\n";
        debug(fmt, branchCounter.get(), getTaskCount(), seconds, pool.getParallelism());
        if (maxTotalPromotions != bestSolution.size() && startingPoint.frames.length > 2) {
            debug("max promotions was over-estimated: expected %d, got %d",
                    maxTotalPromotions, bestSolution.size());
            startingPoint.debugPromotionEstimate(bestSolution);
        }
    }

    public int maxAdditionalPromotions(FrameSet state, TxSet alreadyPromoted, TxSet candidates) {
        return Math.min(
                maxTotalPromotions - alreadyPromoted.size(),
                state.computeMaxNumberOfPromotions(candidates)
        );
    }

    class StatusReportState {
        boolean exceptionReported;
        SoftReference<Object> canary;
        Instant timeOfLastReport;
        long branchCountAtLastReport;
        private final DateTimeFormatter timestampFormat;


        private StatusReportState() {
            this.exceptionReported = false;
            this.canary = new SoftReference<>(new Object());
            this.timeOfLastReport = Instant.now();
            this.branchCountAtLastReport = 0;
            this.timestampFormat = DateTimeFormatter.ofPattern("HH:mm a");
        }

        public void report() {
            var now = Instant.now();
            var timeSinceLastReport = Duration.between(timeOfLastReport, now).toSeconds();
            if (timeSinceLastReport >= STATUS_REPORT_FREQUENCY_IN_SECONDS) {
                timeOfLastReport = now;
                String canaryWarning = "";
                if (canary.get() == null) {
                    canaryWarning = ", soft references have been cleared";
                    canary = new SoftReference<>(new Object());
                }
                var branchCount = branchCounter.get();
                var branchesSinceLastReport = branchCount - branchCountAtLastReport;
                var timestamp = ZonedDateTime.now().format(timestampFormat);
                var kBranchesPerSec = branchesSinceLastReport/1000/timeSinceLastReport;
                var branchesSinceInM = branchesSinceLastReport/1000000;
                var totalBranchesInM = branchCount/1000000;
                debug("  %s: %,3dM branches explored (%,dk/sec), %,dM total%s\n",
                        timestamp, branchesSinceInM, kBranchesPerSec, totalBranchesInM, canaryWarning);
                branchCountAtLastReport = branchCount;

                if (branchException != null && !exceptionReported) {
                    exceptionReported = true;
                    debug(branchException.toString());
                    branchException.printStackTrace();
                }
            }
        }
    }

    void debug(String format, Object ... args) {
        if (!format.endsWith("\n")) format = format + "\n";
        System.out.printf(debugPrefix + format, args);
    }
}