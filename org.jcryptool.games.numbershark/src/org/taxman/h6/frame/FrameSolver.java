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

package org.taxman.h6.frame;

import org.taxman.h6.search.SearchManager;
import org.taxman.h6.game.Board;
import org.taxman.h6.game.OptimalResult;
import org.taxman.h6.game.Solution;
import org.taxman.h6.bombus.Apiary;
import org.taxman.h6.bombus.BombusSolution;
import org.taxman.h6.bombus.Namer;
import org.taxman.h6.game.Solver;
import org.taxman.h6.oldsearch.OldSearch;
import org.taxman.h6.util.TxList;
import org.taxman.h6.util.TxPredicate;
import org.taxman.h6.util.TxSet;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


public class FrameSolver implements Solver {
    // debug output flags
    public static boolean printFrames = false;
    public static boolean printAccelerations = false;
    public static boolean printAccelerationFailures = false;
    public static boolean printSearch = false;
    public static boolean printCacheStats = false;

    // some debugging modes
    public static boolean verifyAccelerations = false;
    public static boolean useOldSearch = false;



    private final Map<Integer, BombusSolution> solutionMap = new HashMap<>();
    private int countOfAccelerated = 0;

    public BombusSolution solve(int n) {
        var sln = new Turbo(n).solve();
        sln.verify(n);
        solutionMap.put(n, sln);
        return sln;
    }

    public void printInternalsReport(PrintStream ps) {
        int accCount = getCountOfAccelerated();
        String g2 = accCount == 1 ? "game" : "games";
        System.out.printf("accelerated %d %s\n", accCount, g2);
    }

    public int getCountOfAccelerated() {
        return countOfAccelerated;
    }

    BombusSolution solveBasedOnPrevOnly(int n) {
        return new Turbo(n).solveBasedOnPrev();
    }

    BombusSolution solveTheHardWay(int n) {
        return new Turbo(n).solveTheHardWay();
    }

    BombusSolution loadPreviouslyComputed(int n) {
        var optResult = OptimalResult.get(n);
        BombusSolution result = null;
        if (optResult != null && optResult.moves != null) {
            result = BombusSolution.upgrade(new Solution(Board.of(n), TxList.of(optResult.moves)));
        }
        return result;
    }

    private class Turbo {
        final private int n;
        final private Board board;
        final private BaseFrame frame;

        private Turbo(int n) {
            this.n = n;
            this.board = Board.of(n);
            this.frame = FrameBuilder.build(board);
            if (printFrames) {
                frame.debugDump(System.out);
            }
        }

        BombusSolution solve() {
            BombusSolution sln = solveBasedOnPrev();
            if (sln == null) sln = solveTheHardWay();
            return sln;
        }

        private void verifyAcceleration(BombusSolution sln) {
            if (n < 2) return;
            System.out.println("verifying acceleration for " + n);
            var sln2 = solveTheHardWay();
            assert sln.score() == sln2.score() : "accelerated solution for " + n + " did not verify";
        }

        private BombusSolution solveBasedOnPrev() {
            if (getPrevious() == null && n > 1) return null;
            BombusSolution result = board.fm.isPrime(n) ? solutionForPrime() : reusePrev();
            if (result != null) {
                ++countOfAccelerated;
                if (printAccelerations) {
                    System.out.println("  accelerated " + n);
                }
            }
            return result;
        }

        /**
         *  Primes are easy: just replace the largest prime that's the first move in the solution to N-1 with
         *  the new prime, N.  The method reusePrev performs a generalization of this function that works for
         *  some numbers that aren't prime, but I'm keeping this special case because it's a fast shortcut.
         */
        private BombusSolution solutionForPrime() {
            if (printSearch) {
                System.out.println("  n is prime, so solution is built from solution to n-1");
            }
            var prev = getPrevious();
            var newMoves = prev.moves.toArray();
            if (newMoves.length == 0) newMoves = new int[1];  // the solution to 1 has no moves, so need this for 2
            newMoves[0] = n;
            return new BombusSolution(board, TxList.of(newMoves), prev.promotions);
        }

        private BombusSolution solveTheHardWay() {
            return spinDown(getMaxPromotionSum());
        }

        private int getMaxPromotionSum() {
            var a = new Apiary(board, new Namer());
            var inTheBag = a.getSolution().sum();
            var prev = getPrevious();
            return (prev != null) ? prev.score() + n - inTheBag : -1;
        }

        private BombusSolution getPrevious() {
            var result = solutionMap.getOrDefault(n-1, null);
            if (result == null) result = loadPreviouslyComputed(n-1);
            //if (result == null) throw new RuntimeException("cannot find solution to " + (n-1));
            return result;
        }

        private BombusSolution spinDown(int maxPromotionSum) {
            var maxPromotions = frame.estimateMaxPromotions(0);
            var candidates = frame.allCandidateNumbersIncludingDownstream();
            if (candidates.largest(maxPromotions).sum() < maxPromotionSum) {
                maxPromotionSum = candidates.largest(maxPromotions).sum();
            }


            int minSum = Math.max(maxPromotionSum-n, 0);

            if (printSearch) {
                System.out.printf("  searching for %d promotions totaling between %d and %d among %d numbers\n",
                        maxPromotions, maxPromotionSum, minSum, candidates.size());
            }

            TxSet promotions;
            if (useOldSearch) {
                var p = makeOldPredicate();
                promotions = OldSearch.findLargest(candidates, maxPromotions, maxPromotionSum, minSum, n, p);
            } else {
                promotions = SearchManager.findLargest(board, frame);
            }

            if (printSearch) {
                System.out.printf("  found %d promotions totaling %d: %s\n",
                        promotions.size(), promotions.sum(), promotions);
            }

            if (useOldSearch && printCacheStats) {
                System.out.println(frame.getCacheStats());
            }

            Apiary a = new Apiary(board, promotions, new Namer());
            return new BombusSolution(board, a.getSolution(), promotions);
        }

        private TxPredicate<TxSet> makeOldPredicate() {
            return new TxPredicate<>(c -> frame.fits(EmptySet, c, EmptySet));
        }

        private BombusSolution reusePrev() {
            var a1 = new Apiary(board, new Namer());
            var a1Candidates = a1.getPromotionCandidateNumbers();
            if (a1Candidates.size() == 0) return new BombusSolution(board, a1.getSolution(), EmptySet);

            var prevMoves = TxSet.of(getPrevious().moves);
            var maxPromotions = frame.estimateMaxPromotions(0);
            var recycledPromotions = TxSet.and(a1Candidates, prevMoves);
            var prudentPromotions = recycledPromotions.largest(maxPromotions);
            var newlyImpossibleMoves = TxSet.and(a1.getImpossible(), prevMoves);
            var idealMoves = TxSet.or(prevMoves, n);
            idealMoves = TxSet.subtract(idealMoves, newlyImpossibleMoves);
            Apiary a2 = new Apiary(board, prudentPromotions, new Namer());
            var newMoves = a2.getSolution();
            BombusSolution result = null;
            if (newMoves.sum() == idealMoves.sum()) {
                result = new BombusSolution(board, newMoves, prudentPromotions);
                if (printSearch) {
                    System.out.printf("  absolutely ideal score: %,d\n", TxSet.or(prevMoves, n).sum());
                    System.out.printf("  sum of newly impossible moves: %,d\n", newlyImpossibleMoves.sum());
                    System.out.printf("           we settle for: %,d\n", idealMoves.sum());
                    System.out.printf("   using %d of %d possible promotions\n", prudentPromotions.size(), maxPromotions);
                    System.out.printf(
                            "  found optimal promotions using previous solution: %d promotions totaling %d: %s\n",
                            prudentPromotions.size(), prudentPromotions.sum(), prudentPromotions
                    );
                }
            } else if (printAccelerationFailures) {
                int newSum = newMoves.sum();
                int idealSum = idealMoves.sum();
                int delta = idealSum - newSum;
                var fmt = "reusePrev: scored %,d, %,d less than the score of %,d necessary to reuse previous solution";
                System.out.printf("  " + fmt + "\n", newSum, delta, idealSum);
            }

            if (verifyAccelerations && result != null) verifyAcceleration(result);
            return result;
        }
    }
}