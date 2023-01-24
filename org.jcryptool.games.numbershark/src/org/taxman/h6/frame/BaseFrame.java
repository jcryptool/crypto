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

import org.taxman.h6.bombus.Hive;
import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import static org.taxman.h6.util.TxUnmodifiableSet.EmptySet;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class BaseFrame {
    public final BaseFrame downstream;
    public final int myLevel;

    protected BaseFrame(int level) {
        this.myLevel = level;
        this.downstream = null;
    }

    public BaseFrame(BaseFrame downstream) {
        this.downstream = downstream;
        this.myLevel = downstream.myLevel - 1;
    }

    public BaseFrame addFrame(List<Hive> hives) {
        return new WorkingFrame(hives, this);
    }

    public void debugDump(PrintStream ps) {
        System.out.println();
    }

    public String getName() {
        return "(root)";
    }

    public int freeFactorCount() {
        return 0;
    }

    public TxSet factors() {
        return EmptySet;
    }

    public int estimateMaxPromotions(int upstreamOffer) {
        return 0;
    }

    public TxSet allCandidateNumbersIncludingDownstream() {
        return EmptySet;
    }

    public List<TxSet> allCandidateNumbersByFrame() {
        return new ArrayList<>();
    }

    public TxSet getImpossible() {
        return EmptySet;
    }

    public boolean fits(TxSet toPromote, TxSet allPromotions, TxUnmodifiableSet allRemovals) {
        return true;
    }

    public FrameTestResult fancyFit(TxSet toPromote, TxSet allPromotions, TxSet allRemovals) {
        return FrameTestResult.reportSuccess(EmptySet);
    }

    public String getCacheStats() {
        return "";
    }

    public List<BaseFrame> getAllFrames() {
        return new ArrayList<>();
    }

    public void finishFrameSetup() {
        finishFrameSetup(null);
    }

    protected void finishFrameSetup(BaseFrame upstream) {
        if (downstream != null) downstream.finishFrameSetup(this);
    }

    TxSet promotionCandidates() {
        return EmptySet;
    }

    public TxSet remainingSources() {
        return TxSet.empty();
    }

    public TxSet remainingFactors() {
        return TxSet.empty();
    }
}