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

import java.util.Arrays;


public class Branch {
    public final FrameSet gameState;
    private final long[] alreadyPromoted;
    private final long[] candidates;


    public Branch(FrameSet gameState, TxSet alreadyPromoted, TxSet candidates) {
        this.gameState = gameState;
        this.alreadyPromoted = alreadyPromoted.bits.toLongArray();
        this.candidates = candidates.bits.toLongArray();
    }

    public String toString() {
        var prom = getAlreadyPromoted();
        var cand = getCandidates();
        return String.format("%,d=%s with %d remaining selections and %d candidates %s", getAlreadyPromoted().sum(),
                prom, gameState.computeMaxNumberOfPromotions(cand),  cand.size(), cand);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Branch)) return false;
        if (this == obj) return true;
        var other = (Branch) obj;
        return gameState.equals(other.gameState) && Arrays.equals(alreadyPromoted, other.alreadyPromoted)
                && Arrays.equals(candidates, other.candidates);
    }

    public TxUnmodifiableSet getAlreadyPromoted() {
        return TxUnmodifiableSet.of(alreadyPromoted);
    }

    public TxUnmodifiableSet getCandidates() {
        return TxUnmodifiableSet.of(candidates);
    }

}