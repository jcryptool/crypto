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

import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;


public class LowerBoundEstimator {
    private final FrameSet startingPoint;
    private final TxUnmodifiableSet allPromotionCandidates;

    private final String debugPrefix;

    private final static int MAX_DROPOUT_COMBO_SIZE = 3;

    public LowerBoundEstimator(FrameSet startingPoint, TxUnmodifiableSet allPromotionCandidates, String debugPrefix) {
        this.startingPoint = startingPoint;
        this.allPromotionCandidates = allPromotionCandidates;
        this.debugPrefix = debugPrefix;
    }

    /**
     * Come up with a fairly quick but reasonably good lower bound on the optimal solution.  Applies a greedy
     * algorithm and then tries to improve upon the score the greedy algorithm gets it by dropping out
     * combinations of numbers from the greedy solution.
     */
    public static TxUnmodifiableSet enhancedGreedy(FrameSet state, TxUnmodifiableSet candidates, String debugPrefix) {
        return new LowerBoundEstimator(state, candidates, debugPrefix).enhancedGreedy();
    }

    public static TxUnmodifiableSet greedy(FrameSet state,
                                           TxUnmodifiableSet candidates, String debugPrefix) {
        return new LowerBoundEstimator(state, candidates, debugPrefix).findGreedySolution().sln.getAlreadyPromoted();
    }

    private TxUnmodifiableSet enhancedGreedy() {
        var greedy = findGreedySolution();
        if (SearchManager.debugPrintDetail) {
            debug("initial promotion sum:  %d\n", greedy.sln.getAlreadyPromoted().sum());
        }
        SkipResult sr = improveSkips(greedy);
        if (SearchManager.debugPrintDetail) {
            debug("enhanced promotion sum: %d\n", sr.sln.getAlreadyPromoted().sum());
        }

        return sr.sln.getAlreadyPromoted();
    }

    private static class SkipResult {
        public final Branch sln;
        public final List<Integer> skipPositions;
        public final TxUnmodifiableSet skippedCandidates;

        private SkipResult(Branch sln, List<Integer> skipPositions, TxUnmodifiableSet skippedCandidates) {
            this.sln = sln;
            this.skipPositions = skipPositions;
            this.skippedCandidates = skippedCandidates;
        }
    }

    private SkipResult improveSkips(SkipResult initial) {
        var allPositions = TxSet.of(IntStream.rangeClosed(1, initial.skipPositions.size()-1));
        return TxSet.combinationsUpToSize(allPositions, MAX_DROPOUT_COMBO_SIZE)
                .map(moreSkips->addToSkips(moreSkips, initial.skipPositions))
                .map(skipAttempt ->
                        findGreedySolutionWithSkips(startingPoint, allPromotionCandidates, skipAttempt))
                .reduce(initial, LowerBoundEstimator::chooseLarger);
    }

    private static List<Integer> addToSkips(TxSet moreSkips, List<Integer> initialSkips) {
        var result = new ArrayList<>(initialSkips);
        // since the smallest number a TxSet can hold is 1, but we have a list (a zero-based object),
        // subtract one from everything in the set
        moreSkips.stream()
                .map(n->n-1)
                .forEach(n -> result.set(n, result.get(n) + 1));
        return result;
    }

    private static SkipResult chooseLarger(SkipResult incumbent, SkipResult challenger) {
        var incumbentSum = incumbent.sln.getAlreadyPromoted().sum();
        var challengerSum = challenger.sln.getAlreadyPromoted().sum();
        return incumbentSum >= challengerSum ? incumbent : challenger;
    }

    private SkipResult findGreedySolution() {
        var initialSkips = Collections.nCopies(startingPoint.computeMaxNumberOfPromotions()+1, 0);
        return findGreedySolutionWithSkips(startingPoint, allPromotionCandidates, initialSkips);
    }

    /**
     * The basic (greedy) idea is to repeatedly take the largest successful candidate until no more candidates
     * can be taken.  This method adds to that the concept of skipping successful candidates.  If a candidate
     * is successful but the list of skips says to skip over some number of successful candidates in this solution
     * position, then do that instead.
     */
    private static SkipResult findGreedySolutionWithSkips(FrameSet gameState, TxSet candidates,
                                                          List<Integer> skipsBySolutionPosition) {
        //debug("skips: " + skipsBySolutionPosition);
        TxSet sln = TxSet.empty();
        TxSet skippedCandidates = TxSet.of();
        FrameSet fs = gameState;
        var skips = skipsBySolutionPosition.get(0);
        for (int n = candidates.max(); n > 0;  n = candidates.nextHighest(n)) {
            var newFs = fs.promote(n);
            if (newFs != null) {
                if (skips-- == 0) {
                    fs = newFs;
                    candidates = fs.narrowCandidateSet(candidates);
                    sln.append(n);
                    skips = skipsBySolutionPosition.get(sln.size());
                } else {
                    skippedCandidates.append(n);
                }
            }
        }
        return new SkipResult(new Branch(fs, sln, EmptySet), skipsBySolutionPosition, skippedCandidates.unmodifiable());
    }

    void debug(String format, Object ... args) {
        System.out.printf(debugPrefix + format, args);
    }
}