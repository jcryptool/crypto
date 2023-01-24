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
import java.util.stream.IntStream;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;


public class UpperBoundEstimator {
    private final TxSet candidateNumbers;
    private final FrameSet frameSet;
    private final PreclusionList[] preclusions;

    private final int CONTEXT_DEPTH = 2;

    /*
     * Theorem: the set of promotions with the maximum sum also has the maximum number of promotions.
     *  I'm sure it's true for a single frame (taxman mini), but the game we need to play here as two frames,
     *  and I wish I had a proof for this.
     */
    public FrameSet reviseFreedoms(FrameSet state, TxUnmodifiableSet candidates, TxUnmodifiableSet bestKnownSolution) {
        var fullGameMaxPromotions = state.computeMaxNumberOfPromotions(candidates);
        if (bestKnownSolution.size() != fullGameMaxPromotions && state.frames.length > 2) {
            // one of two things is happening here
            // 1) the best known solution has fewer than the optimal number of promotions
            // 2) the way the frameset computes the max number of promotions is over-estimating the true maximum

            if (SearchManager.debugPrintDetail) {
                debug("verifying max promotions of %d", fullGameMaxPromotions);
            }

            // Maybe there's room for more promotions into frame 0.  If not, reduce frame 0 freedoms to make
            // that obvious.
            var front = state.frames[0];
            var frame1 = state.frames[1];
            var smallerState = new FrameSet(new Frame[]{front, frame1}, state.candidateMapper, state.math);
            var candidatesFrom1 = TxSet.and(candidateNumbers, frame1.getFactors()).unmodifiable();
            var mgr = SearchManager.create(smallerState, candidatesFrom1, "    ");
            var bestPossibleNumPromotionsForSmallGame = Math.min(front.freedoms(), frame1.freedoms());
            var numPromotionsForSmallGameGreedySolution = mgr.bestSolution.size();
            var numPromotionsForFullGameIfGreedyIsOptimal =
                    state.setFrameZeroFreedoms(mgr.bestSolution.size()).computeMaxNumberOfPromotions(candidates);

            if (numPromotionsForSmallGameGreedySolution == bestPossibleNumPromotionsForSmallGame) {
                // If the size of the greedy solution to the small game is the theoretical maximum, then
                // we don't have to play the small game, we can just use the theoretical max.
                state = state.setFrameZeroFreedoms(numPromotionsForSmallGameGreedySolution);
            } else if (numPromotionsForFullGameIfGreedyIsOptimal != fullGameMaxPromotions) {
                // if the result of playing the small game could actually change the number of promotions possible
                // in the full game, then play the small game to figure out the actual max.
                var promotions = mgr.findOptimalPromotions();
                state = state.setFrameZeroFreedoms(promotions.size());
            }
            if (SearchManager.debugPrintDetail) {
                var newMax = state.computeMaxNumberOfPromotions(candidates);
                if (fullGameMaxPromotions != newMax) {
                    debug("reduced max promotions from %d to %d\n", fullGameMaxPromotions, newMax);
                } else {
                    debug("max promotions remains %d\n", fullGameMaxPromotions);
                }
            }
        }

        return state;
    }

    private static class Preclusion {
        public final TxUnmodifiableSet context;
        public final TxUnmodifiableSet precluded;

        private Preclusion(TxSet context, TxSet precluded) {
            this.context = context.unmodifiable();
            this.precluded = precluded.unmodifiable();
        }
    }

    private static class PreclusionList extends ArrayList<Preclusion> {
    }

    private UpperBoundEstimator(TxSet candidateNumbers, FrameSet fs) {
        this.candidateNumbers = candidateNumbers;
        this.frameSet = fs;
        this.preclusions = IntStream.rangeClosed(0, candidateNumbers.max())
                .mapToObj(i->new PreclusionList())
                .toArray(PreclusionList[]::new);
    }

    public static UpperBoundEstimator create(TxUnmodifiableSet candidates, FrameSet startingPoint) {
        var result = new UpperBoundEstimator(candidates, startingPoint);
        result.findPreclusions();
        return result;
    }

    private void findPreclusions() {
        candidateNumbers.stream()
                .parallel()
                .forEach(n -> {
                    var newState = frameSet.promote(n);
                    var candidates = TxSet.of(candidateNumbers.filter(x -> x < n && newState.containsFactor(x)));
                    findPreclusions(n, newState, candidates, EmptySet, CONTEXT_DEPTH);
        });

    }

    private void findPreclusions(int x, FrameSet state, TxSet candidates, TxSet context, int depth) {
        var precluded = TxSet.empty();
        candidates.stream()
                .parallel()
                .forEach(n -> {
                    var newState= state.promote(n);
                    if (newState == null) {
                        synchronized (precluded) {
                            precluded.append(n);
                        }
                    } else if (depth > 1) {
                        var newContext = TxUnmodifiableSet.or(context, x);
                        var newCandidates = TxSet.of(
                                candidateNumbers.filter(z -> z < n && newState.containsFactor(z))
                        );
                        findPreclusions(n, newState, newCandidates, newContext, depth-1);
                    }
                });
        if (!precluded.isEmpty()) {
            synchronized (preclusions[x]) {
                //debug("preclusions for %d include context %s precludes %s\n", x, context, precluded);
                preclusions[x].add(new Preclusion(context, precluded));
            }
        }
    }


    protected TxSet getPreclusions(int n, TxSet alreadyPromoted) {
        TxSet result = TxSet.of();
        for(var p: preclusions[n]) {
            if (alreadyPromoted.contains(p.context)) result.appendAll(p.precluded);
        }
        return result;
    }

    /**
     *  Remove precomputed numbers (numbers we know won't work with n)
     */
    protected TxSet removePrecluded(TxSet candidates, int n, TxSet alreadyPromoted) {
        var prec = getPreclusions(n, alreadyPromoted);
        return TxSet.subtract(candidates, prec);
    }

    public int upperBound(FrameSet gameState, TxSet candidates, int maxSize, TxSet alreadyPromoted) {
        return blameGame(gameState, candidates, maxSize);
    }

    /**
     * There are a lot of possible ways to estimate an upper bound for a set of candidates.  The simplest is to
     * just assume that the largest numbers will all be promoted successfully, like this:
     *      candidates.sumOfLargest(maxSize)
     * But usually at least one of those promotions is going to fail, and that means one or more smaller numbers
     * will be brought in to replace the failures.  This function assumes there will be only a single failure.
     * It starts with the largest candidate and makes promotions until a candidate can't be promoted.  It then
     * removes that number and replaces it with the largest number that wasn't included in the original estimate.
     */
    private int blameGame(FrameSet state, TxSet candidates, int maxSize) {
        var largestThatFit = largestThatFitInFrames(state, candidates, maxSize);

        var fs = state;
        var successCount=0;
        var failNumber = 0;
        for (int n = largestThatFit.max(); n > 0 && successCount < maxSize; n = largestThatFit.nextHighest(n)) {
            if (!fs.containsFactor(n)) {
                failNumber = n;
                break;
            }
            fs = fs.promote(n);
            if (fs == null) {
                failNumber = n;
                break;
            }
            ++successCount;
        }

        var bestPossible = largestThatFit.sum();
        return  failNumber == 0 ?  bestPossible : bestPossible - failNumber + getRunnerUp(candidates, largestThatFit);
    }

    /**
     * It would be nice if we could know how many numbers were going to be promoted out of each frame, but it's
     * impossible to know for sure.  What we do know is that there's a maximum number of promotions for each frame.
     * That knowledge is particularly useful for the first and last frames, so this method returns a candidate
     * list that only includes the largest candidates that could possibly fit for three groups:
     * 1) The first frame that can allow promotions (frame 1)
     * 2) The last frame
     * 3) All frames together
     */
    private TxSet largestThatFitInFrames(FrameSet state, TxSet candidates, int maxSize) {
        TxSet largest = TxSet.of();
        var frames = state.frames;
        if (frames.length < 4) {
            largest = candidates.largest(maxSize);
        } else {
            int lowEnd = Math.min(frames[0].freedoms(), frames[1].freedoms());
            int highEnd = Math.min(frames[frames.length - 2].freedoms(), frames[frames.length - 1].freedoms());
            int successCount = 0;
            for (int n = candidates.max(); n > 0 && successCount < maxSize; n = candidates.nextHighest(n)) {
                int frameForN = state.mapToFrame(n);
                if (frameForN == 1) {
                    if (lowEnd > 0) {
                        largest.append(n);
                        --lowEnd;
                        ++successCount;
                    }
                } else if (frameForN == frames.length-1) {
                    if (highEnd > 0) {
                        largest.append(n);
                        --highEnd;
                        ++successCount;
                    }
                } else {
                    largest.append(n);
                    ++successCount;
                }
            }
        }

        return largest;
    }

    private int getRunnerUp(TxSet candidates, TxSet bid) {
        int result;
        if (bid.isEmpty()) {
            result = candidates.max();
        } else {
            result = candidates.nextHighest(bid.min());
        }
        if (result < 0) result = 0;
        return result;
    }

    void debug(String format, Object ... args) {
        if (!format.endsWith("\n")) format = format + "\n";
        System.out.printf("  " + format, args);
    }
}