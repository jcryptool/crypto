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


public class Frame {
    public final int frameNumber;
    private final TxUnmodifiableSet moves;
    private final TxUnmodifiableSet factors;
    private final int freedoms;

    Frame(int number, TxUnmodifiableSet moves, TxUnmodifiableSet factors, int freedoms) {
        this.frameNumber = number;
        this.moves = moves;
        this.factors = factors;
        this.freedoms = freedoms;
    }

    Frame(int number, TxUnmodifiableSet moves, TxUnmodifiableSet factors) {
        this(number, moves, factors, (factors != null && moves != null) ? factors.size() - moves.size() : 0);
    }

    protected Frame(Frame f) {
        this(f.frameNumber, f.getMoves(), f.getFactors(), f.freedoms());
    }

    public TxSet allNumbers() {
        return TxSet.or(getMoves(), getFactors());
    }

    public TxUnmodifiableSet getMoves() {
        return moves;
    }

    public TxUnmodifiableSet getFactors() {
        return factors;
    }

    public int freedoms() {
        return freedoms;
    }

    public Frame promoteInto(int n, FactorProvider math) {
        return promoteInto(n, math, null);
    }

    public Frame promoteInto(int n, FactorProvider math, FrameMapper mapper) {
        if (mapper != null) mapper.promotionCounter.incrementAndGet();
        TxUnmodifiableSet mv = getMoves();
        TxUnmodifiableSet fac = getFactors();
        TxSet remainingMoves = TxSet.or(mv, n);
        TxSet remainingFactors = TxSet.of(fac);
        if (!isWinnable(remainingMoves, remainingFactors, math)) return null;

        TxSet movesToKeep = TxSet.or(mv, n);
        TxSet factorsToKeep = TxSet.of(fac);
        // n is the only number that might have a single factor, but it could start a chain
        // reaction.  We only need to do the reduction if n will be removed, so you could check
        // to see if n only has a single factor and skip the reduction if it has more than one.
        // I used to write it like this:
        //      if (TxSet.and(math.getFactors(n), factorsToKeep).size() == 1)
        // but I no longer have a quick/easy way to do this check
        reduce(movesToKeep, factorsToKeep, math);
        var factorsForNewFrame = fac.equals(factorsToKeep) ? fac : factorsToKeep;
        return new Frame(frameNumber, movesToKeep.unmodifiable(), factorsForNewFrame.unmodifiable(), freedoms - 1);
    }

    public Frame promoteOutOf(int n, FactorProvider math) {
        return promoteOutOf(n, math, null);
    }

    public Frame promoteOutOf(int n, FactorProvider math, FrameMapper mapper) {
        if (mapper != null) mapper.promotionCounter.incrementAndGet();
        TxUnmodifiableSet mv = getMoves();
        TxUnmodifiableSet fac = getFactors();
        TxSet remainingMoves = TxSet.of(mv);
        assert fac.contains(n);
        TxSet remainingFactors = TxSet.subtract(fac, n);
        if (!isWinnable(remainingMoves, remainingFactors, math)) return null;

        TxSet movesToKeep = TxSet.of(mv);
        TxSet factorsToKeep = TxSet.subtract(fac, n);
        reduce(movesToKeep, factorsToKeep, math);
        var movesForNewFrame = mv.equals(movesToKeep) ? mv : movesToKeep;
        return new Frame(frameNumber, movesForNewFrame.unmodifiable(), factorsToKeep.unmodifiable(), freedoms - 1);
    }

    public boolean canPromoteInto(int n, FactorProvider math, FrameMapper mapper) {
        if (mapper != null) mapper.promotionCounter.incrementAndGet();
        TxSet remainingMoves = TxSet.or(getMoves(), n);
        TxSet remainingFactors = TxSet.of(getFactors());
        return isWinnable(remainingMoves, remainingFactors, math);
    }

    public boolean canPromoteOutOf(int n, FactorProvider math, FrameMapper mapper) {
        if (mapper != null) mapper.promotionCounter.incrementAndGet();
        TxSet remainingMoves = TxSet.of(getMoves());
        TxSet remainingFactors = TxSet.subtract(getFactors(), n);
        return isWinnable(remainingMoves, remainingFactors, math);
    }

    /**
     * These sets are going to be torn up during the evaluation process, so you can't make use of their
     * values after the call is complete.
     * @return true if all the moves can be taken using the given factors (there may be factors left over).
     */
    private boolean isWinnable(TxSet remainingMoves, TxSet remainingFactors, FactorProvider math) {
        if (remainingMoves.size() > remainingFactors.size()) return false;
        if (remainingMoves.isEmpty()) return true;
        boolean progress = true;

        while (progress) {
            progress = false;
            // take out any moves that have only a single factor
            for (int m = remainingMoves.max(); m > 0;  m = remainingMoves.nextHighest(m)) {
                int[] factorsForM = math.getFactors(m);
                int matchCount = 0;
                int factor = 0;
                for (int q: factorsForM) {
                    if (remainingFactors.contains(q)) {
                        ++matchCount;
                        factor = q;
                    }
                }
                switch (matchCount) {
                    case 0:
                        return false;
                    case 1:
                        remainingMoves.remove(m);
                        if (remainingMoves.isEmpty()) return true;
                        remainingFactors.remove(factor);
                        progress = true;
                }
            }
            // take out any factors that have only a single move
            for (int f = remainingFactors.max(); f > 0;  f = remainingFactors.nextHighest(f)) {
                var compositesForF = math.getComposites(f);
                int matchCount = 0;
                int move = 0;
                for (int q: compositesForF) {
                    if (remainingMoves.contains(q)) {
                        ++matchCount;
                        move = q;
                    }
                }
                if (matchCount == 1) {
                    remainingMoves.remove(move);
                    if (remainingMoves.isEmpty()) return true;
                    remainingFactors.remove(f);
                    progress = true;
                }
            }
        }
        return false;
    }

    public Frame reduce(FactorProvider math) {
        var mv = getMoves();
        var fac = getFactors();
        TxSet movesToKeep = TxSet.of(mv);
        TxSet factorsToKeep = TxSet.of(fac);
        reduce(movesToKeep, factorsToKeep, math);
        var movesForNewFrame = mv.equals(movesToKeep) ? mv : movesToKeep;
        var factorsForNewFrame = fac.equals(factorsToKeep) ? fac : factorsToKeep;
        return new Frame(frameNumber, movesForNewFrame.unmodifiable(), factorsForNewFrame.unmodifiable());
    }

    private void reduce(TxSet movesToKeep, TxSet factorsToKeep, FactorProvider math) {
        boolean progress = true;
        while (progress) {
            if (movesToKeep.isEmpty()) return;
            progress = false;
            // take out any moves that have only a single factor
            for (int m = movesToKeep.max(); m > 0;  m = movesToKeep.nextHighest(m)) {
                int[] factorsForM = math.getFactors(m);
                int matchCount = 0;
                int factor = 0;
                for (int q: factorsForM) {
                    if (factorsToKeep.contains(q)) {
                        ++matchCount;
                        factor = q;
                    }
                }
                switch (matchCount) {
                    case 0:
                        assert false;
                    case 1:
                        movesToKeep.remove(m);
                        factorsToKeep.remove(factor);
                        progress = true;
                }
            }
        }
    }

    public String toString() {
        TxUnmodifiableSet moves = getMoves();
        TxUnmodifiableSet factors = getFactors();
        String result;
        if (moves.size() == 0 && factors.size() == 0) {
            result = "(empty)";
        } else {
            result = String.format("%d->%d %s -> %s", moves.size(), factors.size(), moves, factors);
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Frame)) return false;
        if (this == obj) return true;
        Frame other = (Frame) obj;
        TxUnmodifiableSet moves = getMoves();
        TxUnmodifiableSet factors = getFactors();
        TxUnmodifiableSet otherMoves = other.getMoves();
        TxUnmodifiableSet otherFactors = other.getFactors();
        return moves.equals(otherMoves) && factors.equals(otherFactors);
    }

    public void debugDumpFactors(FactorProvider math) {
        TxUnmodifiableSet moves = getMoves();
        TxUnmodifiableSet factors = getFactors();
        moves.stream().forEach(n ->
                System.out.printf("    %d: %s\n", n, TxSet.and(TxSet.of(math.getFactors(n)), factors))
        );
    }
}