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

import org.taxman.h6.bombus.Apiary;
import org.taxman.h6.bombus.BombusSolution;
import org.taxman.h6.bombus.Namer;
import org.taxman.h6.game.Board;
import org.taxman.h6.game.OptimalResult;
import org.taxman.h6.game.Solver;
import org.taxman.h6.util.TxPredicate;
import org.taxman.h6.util.TxSet;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;


public class GreedySolver implements Solver {
    public static boolean printPromotions = false;
    public static boolean printUpgrades = false;

    public boolean warnOnImperfectScore = true;
    private final int maxComboSize;

    public GreedySolver(int maxComboSize) {
        this.maxComboSize = maxComboSize;
    }

    public BombusSolution solve(int n) {
        var board = Board.of(n);
        var frame = FrameBuilder.build(board);
        var p = new TxPredicate<TxSet>(c -> frame.fits(EmptySet, c, EmptySet));
        var candidates = frame.allCandidateNumbersIncludingDownstream();
        int maxPromotions = frame.estimateMaxPromotions(0);
        var greedyPromotions = new GreedyPromotionMaximizer(candidates, maxPromotions, p).find();
        var b = new Apiary(board, greedyPromotions, new Namer());
        var sln = new BombusSolution(board, b.getSolution(), greedyPromotions);
        var opt = OptimalResult.get(n);
        if (opt != null) {
            if (warnOnImperfectScore && sln.score() != opt.score) {
                System.out.printf("WARNING: greedy score is %,d but optimal score is %,d (a difference of %,d)\n",
                        sln.score(), opt.score, opt.score-sln.score());
            }
        }
        return sln;
    }

    public void printInternalsReport(PrintStream ps) {
        // nothing to say just yet
    }

    public class GreedyPromotionMaximizer {
        private final TxSet allCandidates;
        private final TxPredicate<TxSet> predicate;
        private final int maxPromotions;

        public GreedyPromotionMaximizer(TxSet allCandidates, int maxPromotions, TxPredicate<TxSet> predicate) {
            this.allCandidates = allCandidates;
            this.predicate = predicate;
            this.maxPromotions = maxPromotions;
        }

        private TxSet baseCase(TxSet numbers) {
            var result = TxSet.empty();
            for (int n : numbers.descendingArray()) {
                TxSet newTry = TxSet.or(result, n);
                if (predicate.test(newTry)) result = newTry;
            }
            return result;
        }

        private List<Integer> promotionsToSkipVector(TxSet promotions) {
            TxSet promotionsThusFar = TxSet.empty();
            int skips = 0;
            List<Integer> skipVector = new ArrayList<>();
            for (int candidate : allCandidates.descendingArray()) {
                if (!predicate.test(TxSet.or(promotionsThusFar, candidate))) continue;
                if (promotions.contains(candidate)) {
                    promotionsThusFar.append(candidate);
                    skipVector.add(skips);
                    skips = 0;
                } else {
                    ++skips;
                }
            }
            for (int i = skipVector.size(); i < maxPromotions; i++) skipVector.add(0);
            return skipVector;
        }

        private TxSet skipVectorToPromotions(List<Integer> skipVector) {
            var skiterator = skipVector.iterator();
            int skips = (skiterator.hasNext()) ? skiterator.next() : -1;
            TxSet result = TxSet.empty();
            for (int candidate : allCandidates.descendingArray()) {
                if (!predicate.test(TxSet.or(result, candidate))) continue;
                if (--skips < 0) {
                    result.append(candidate);
                    skips = (skiterator.hasNext()) ? skiterator.next() : -1;
                }
            }
            return result;
        }

        private TxSet find() {
            var bestPromotions = baseCase(allCandidates);

            if (printPromotions) {
                System.out.printf("  greedy search initial promotions: %d=%s\n"
                        , bestPromotions.sum(), bestPromotions);
            }

            while (true) {
                var result = improveSkipVector(bestPromotions);
                if (result.sum() <= bestPromotions.sum()) break;
                if (printUpgrades) {
                    var dropped = TxSet.subtract(bestPromotions, result);
                    var added = TxSet.subtract(result, bestPromotions);
                    System.out.printf("    added %s, dropped %s\n", added, dropped);
                    System.out.printf("    best promotions are now %d = %s\n", result.sum(), result);
                }
                bestPromotions = result;
            }

            if (printPromotions) {
                System.out.printf("  greedy search final promotions: %d=%s\n"
                        , bestPromotions.sum(), bestPromotions);
            }

            return bestPromotions;
        }

        private TxSet improveSkipVector(TxSet base) {
            var bestVector = this.promotionsToSkipVector(base);
            //System.out.printf("best vector is %s\n", bestVector);
            return TxSet.combinationsUpToSize(TxSet.of(IntStream.rangeClosed(1, maxPromotions)), maxComboSize)
                    .parallel()
                    .map(combo -> addToVector(bestVector, combo))
                    .map(this::skipVectorToPromotions)
                    .reduce(base, (best, other) -> best.sum() >= other.sum() ? best : other);
        }
    }

    private static List<Integer> addToVector(List<Integer> vector, TxSet toAdd) {
        var result = new ArrayList<>(vector);
        toAdd.forEach(x -> result.set(x - 1, result.get(x - 1) + 1));
        return result;
    }

}
