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

package org.taxman.h6.bombus;

import org.taxman.h6.frame.FrameTestResult;
import org.taxman.h6.util.TxList;
import org.taxman.h6.util.TxSet;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Hive {
    public final Apiary apiary;
    private String name = "";
    private TxSet sources = TxSet.empty();
    private TxSet factors = TxSet.empty();
    private final TxSet mandatoryMoves = TxSet.empty();
    private final TxSet claimedFactors = TxSet.empty();
    private final TxSet impossibleMoves = TxSet.empty();
    private int freeFactorCount;

    Hive(Apiary apiary) {
        this.apiary = apiary;
    }

    public static Hive merge(List<Hive> hives) {
        assert (hives.size() > 1);
        var first = hives.get(0);
        var result = new Hive(first.apiary);
        result.setName(hives.stream()
                .map(Hive::getName)
                .collect(Collectors.joining("+"))
        );
        hives.forEach(h -> {
            result.sources.appendAll(h.sources);
            result.factors.appendAll(h.factors);
        });
        result.freeFactorCount = result.factors.size();
        return result;
    }

    void addSource(int source) {
        sources.append(source);
    }

    void addFactor(int factor) {
        factors.append(factor);
        freeFactorCount = factors.size();
    }

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void subsume(Hive other) {
        sources.appendAll(other.sources);
        factors.appendAll(other.factors);
        freeFactorCount = factors.size();
    }

    public TxSet sources() {
        //todo: figure out how to have this return an unmodifiable set without calling unmodifiable every time
        return sources;
    }

    public TxSet factors() {
        return factors;
    }

    TxSet allNumbers() {
        return TxSet.or(sources, factors);
    }

    public String toString() {
        String prefix = (name.length() > 0) ? name + ": " : "";
        return prefix + sources + " -> " + factors;
    }

    public Set<Hive> downstream() {
        //return remainingFactors().stream()
        return allNumbers().stream()
                .mapToObj(apiary.board::factors)
                //.mapToObj(apiary::factors)
                .flatMapToInt(TxSet::stream)
                .mapToObj(apiary::getHiveForNumber)
                .filter(h -> h != this)
                .collect(Collectors.toSet());
    }

    public Set<Hive> upstream() {
        return allNumbers().stream()
                .mapToObj(apiary.board::composites)
                .flatMapToInt(TxSet::stream)
                .mapToObj(apiary::getHiveForNumber)
                .filter(h -> h != this)
                .collect(Collectors.toSet());
    }

    public TxSet remainingSources() {
        return TxSet.subtract(sources, TxSet.or(mandatoryMoves, impossibleMoves));
    }

    public TxSet remainingFactors() {
        return TxSet.subtract(factors, claimedFactors);
    }

    // return true if this factor could possibly be used by a move in a downstream hive
    private boolean factorCanGoDownstream(int factor) {
        return apiary.factors(factor).stream()
                .mapToObj(apiary::getHiveForNumber)
                .distinct()
                .anyMatch(h -> h.canAddSourceToThisHive(factor));
    }

    boolean finishSetup() {
        boolean madeChanges = unzip();
        madeChanges |= removeImpossible();

        // is this universally true or just for all the boards I've seen?
        freeFactorCount = factors.size() - sources.size();

        return madeChanges;
    }

    void finishFrameSetup() {
        factors = factors.unmodifiable();
        sources = sources.unmodifiable();
        //limitedFactors = remainingFactors().unmodifiable();  // trying to avoid doing this over and over
        //limitedSources = remainingSources().unmodifiable();  // ditto
    }

    /*
    Iteratively remove:
    1) Sources with only a single factor.  The factor is the only way for us to take this move.
    2) Factors with a single source (assuming the factor can't itself become a source in a downstream hive).
     */
    private boolean unzip() {
        //System.out.println("unzipping " + name + " with free factor count " + freeFactorCount);
        boolean foundSomething = false;
        boolean madeClaim = true;
        while (madeClaim) {
            madeClaim = false;

            // if a factor has only a single composite in the hive, and if that factor can't be promoted
            // and doesn't have any other factors outside the hive, take the move.
            for (int factor: remainingFactors().toArray()) {
                TxSet allComposites = apiary.composites(factor);
                TxSet remainingCompositesInHive = TxSet.and(remainingSources(), allComposites);
                if (remainingCompositesInHive.size() == 1 && !factorCanGoDownstream(factor)) {
                    TxSet availableCompositesOutsideHive = apiary.movesAvailableUsingThisFactorAsTaxOutsideHive(factor);
                    if (availableCompositesOutsideHive.size() == 0) {
                        //System.out.println(sources + " unzip a: " + remainingCompositesInHive.min() + " -> " + factor);
                        mandatoryMoves.append(remainingCompositesInHive.min()); // only one composite
                        claimedFactors.append(factor);
                        madeClaim = true;
                    }
                }
            }

            // Sort of like take-the-highest-prime, but in the hive we expect to be able to take
            // all sources as moves (unless we prove them impossible) so if a source has only
            // a single factor, take the move.
            for (int n: remainingSources().streamDescending().toArray()) {
                TxSet factors = TxSet.subtract(apiary.factors(n), claimedFactors);
                if (factors.size() == 1) {
                    int factor = factors.min();
                    TxSet availableCompositesOutsideHive = apiary.movesAvailableUsingThisFactorAsTaxOutsideHive(factor);
                    if (availableCompositesOutsideHive.size() == 0) {
                        //System.out.println(sources + " unzip b: " + n + " -> " + factors.min());
                        mandatoryMoves.append(n);
                        claimedFactors.append(factor);
                        madeClaim = true;
                        break;  // need to restart because larger sources we already considered might now be eligible
                    }
                }
            }

            foundSomething |= madeClaim;
        }

        return foundSomething;
    }

    boolean removeImpossible() {
        int numImpossibleAtStart = impossibleMoves.size();
        TxSet remainingFactors = remainingFactors();
        TxSet remainingSources = remainingSources();
        int[] sortedSources = remainingSources.streamDescending().toArray();
        TxSet provenSources = TxSet.empty();
        for (int source: sortedSources) {
            int newFreeFactorCount = countFreedoms(TxSet.or(provenSources, source), remainingFactors);
            if (newFreeFactorCount >= 0) {
                provenSources.append(source);
            } else {
                //System.out.println(source + " is impossible with " + remainingFactors);
                impossibleMoves.append(source);
            }
        }

        sources = TxSet.subtract(sources, impossibleMoves);
        return impossibleMoves.size() > numImpossibleAtStart;
    }

    public TxSet getImpossible() {
        return impossibleMoves;
    }

    // return true if this hive might be able to accept n as a source
    boolean canAddSourceToThisHive(int n) {
        return countFreedoms(TxSet.or(sources, n), factors) > -1;
    }

    private boolean okToRemove(int factor) {
        TxSet remainingFactors = TxSet.subtract(remainingFactors(), factor);
        TxSet remainingSources = remainingSources();
        return countFreedoms(remainingSources, remainingFactors) > -1;
    }

    public FrameTestResult worksWithMods(TxSet promoteIntoHive, TxSet promoteOutOfHive, TxSet alreadyRemoved) {
        //System.out.printf("worksWithMods for %s and %s removals\n", getName(), alreadyRemoved);
        TxSet remainingFactors = TxSet.subtract(TxSet.subtract(remainingFactors(), promoteOutOfHive), alreadyRemoved);
        TxSet remainingMoves = TxSet.subtract(TxSet.or(remainingSources(), promoteIntoHive), alreadyRemoved);
        if (remainingMoves.size() > remainingFactors.size()) return FrameTestResult.FAIL;

        TxSet newRemovals = null;
        boolean progress = true;
        while (progress) {
            //modEvalCount.incrementAndGet();
            if (remainingMoves.size() == 0) return FrameTestResult.reportSuccess(newRemovals);
            progress = false;
            // take out any moves that have only a single factor
            for (int m = remainingMoves.max(); m > 0;  m = remainingMoves.nextHighest(m)) {
                TxSet factorsForMove = TxSet.and(apiary.factors(m), remainingFactors);
                switch (factorsForMove.size()) {
                    case 0:
                        return FrameTestResult.FAIL;
                    case 1:
                        int f = factorsForMove.max();
                        remainingMoves.remove(m);
                        remainingFactors.remove(f);
                        if (newRemovals == null) newRemovals = TxSet.empty();
                        newRemovals.appendAll(m, f);
                        progress = true;
                }
            }
            // take out any factors that have only a single move
            for (int f = remainingFactors.max(); f > 0;  f = remainingFactors.nextHighest(f)) {
                TxSet movesForFactor = TxSet.and(apiary.composites(f), remainingMoves);
                if (movesForFactor.size() == 1) {
                    int m = movesForFactor.max();
                    remainingMoves.remove(m);
                    remainingFactors.remove(f);
                    // can't add m and f to removed here because the move has more than one factor, so
                    // we don't know which factor m really should take
                    progress = true;
                }
            }
        }
        return FrameTestResult.FAIL;
    }


        // if all moves can be taken, return the number of free factors.  If not all moves can be taken, return -1
    private int countFreedoms(TxSet moves, TxSet factors) {
        if (moves.size() == 0) {
            return factors.size();
        } else if (moves.size() > factors.size()) {
            return -1;
        } else {
            for (int m: moves.toArray()) {
                TxSet factorsForMove = TxSet.and(apiary.factors(m), factors);
                switch (factorsForMove.size()) {
                    case 0:
                        return -1;  // no moves for factor
                    case 1:
                        // evaluate without this move/factor combo
                        return countFreedoms(TxSet.subtract(moves, m), TxSet.subtract(factors, factorsForMove));
                }
            }

            for (int f: factors.toArray()) {
                TxSet movesForFactor = TxSet.and(apiary.composites(f), moves);
                if (movesForFactor.size() == 1) {
                    // evaluate without this move/factor combo
                    return countFreedoms(TxSet.subtract(moves, movesForFactor), TxSet.subtract(factors, f));
                }
            }
            // If we get here, all moves have more than one factor and all factors have more than one move.
            // As far as I know, this is never a winning situation.
            return -1;
        }
    }

    public TxList getPromotionCandidateNumbers() {
        return TxList.of(
                remainingFactors().stream()
                        .filter(this::okToRemove)
                        .filter(factor -> apiary.factors(factor).stream()
                                .anyMatch(sub -> apiary.getHiveForNumber(sub).canAddSourceToThisHive(factor))
                        )
        );
    }

    public int freeFactorCount() {
        return freeFactorCount;
    }

    public boolean hasFreeFactors() {
        return freeFactorCount > 0;
    }

    TxList getSolution() {
        TxSet src = TxSet.of(sources);
        TxSet snk = TxSet.of(factors);
        TxList moves = new TxList();
        TxList playAtEnd = new TxList();

        while (!src.isEmpty()) {
            boolean progress = false;
            for (int n: src.toArray()) {
                TxSet factorsForMove = TxSet.and(apiary.factors(n), snk);
                if (factorsForMove.size() == 1) {
                    moves.append(n);
                    src = TxSet.subtract(src, n);
                    snk = TxSet.subtract(snk, factorsForMove);
                    progress = true;
                }
            }
            for (int f: snk.toArray()) {
                TxSet movesForFactor = TxSet.and(apiary.composites(f), src);
                if (movesForFactor.size() == 1) {
                    int move = movesForFactor.min();
                    playAtEnd.append(move);
                    src = TxSet.subtract(src, move);
                    snk = TxSet.subtract(snk, f);
                    progress = true;
                }
            }
            assert progress;
        }

        moves.appendAll(playAtEnd.reverse());
        return moves;
    }

    public void debugDump(PrintStream ps) {
        TxList couldBecomeSources = getPromotionCandidateNumbers();

        Set<Hive> remainingDownstream = couldBecomeSources.stream()
                    .mapToObj(apiary::factors)
                    .flatMapToInt(TxSet::stream)
                    .mapToObj(apiary::getHiveForNumber)
                    .filter(h -> h != this)
                    .collect(Collectors.toSet());

        ps.println(this);
        TxSet remainingFactors = remainingFactors();
        TxSet remainingSources = remainingSources();
        if (!remainingSources.isEmpty() || !remainingFactors.isEmpty())
            ps.println("   remaining: " + remainingSources + " -> " + remainingFactors);
        if (!impossibleMoves.isEmpty())
            ps.println("   impossible: " + impossibleMoves);
        if (hasFreeFactors()) {
            String txt = "   freedoms: " + freeFactorCount;
            ps.println(txt);
        }
        //ps.println("   ok to remove: " + TxSet.of(remainingFactors().filter(this::okToRemove)));
        Set<Hive> downstreamHivesInNeed = remainingDownstream.stream()
                .filter(h -> h.freeFactorCount > 0)
                .collect(Collectors.toSet());
        if (downstreamHivesInNeed.size() > 0) {
            String ds = downstreamHivesInNeed.stream()
                    .map(Hive::getName)
                    .collect(Collectors.joining(", "));
            int downstreamCapacity = downstreamHivesInNeed.stream()
                    .mapToInt(Hive::freeFactorCount)
                    .sum();
            int capacity = Math.min(freeFactorCount, downstreamCapacity);
            ps.println("   could offer " + capacity + " to " + ds + " from " + couldBecomeSources);
        } else {
            if (hasFreeFactors())
                ps.println("   no downstream hives with space, could add to this hive");
            else
                ps.println("   closed hive");
        }
        ps.println();
    }

}