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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class FrameSet {

    final Frame[] frames;
    final FrameMapper candidateMapper;
    final FactorProvider math;


    FrameSet(Frame[] frames, FrameMapper candidateMapper, FactorProvider math) {
        this.frames = frames;
        this.candidateMapper = candidateMapper;
        this.math = math;
    }

    private static Frame[] makeFrames(List<TxUnmodifiableSet> columns, FactorProvider math) {
        assert columns.size() % 2 == 0;
        Frame[] frames = new Frame[columns.size()/2];
        for (int i = 0; i < columns.size(); i += 2) {
            frames[i/2] = new Frame(i/2, columns.get(i+1), columns.get(i)).reduce(math);
        }
        return frames;
    }

    /**
     * @param columns: columns from front (the lowest numbers) to highest, so factors then moves for every frame
     * @return a FrameSet built from the given columns
     */
    public static FrameSet create(FactorProvider math, List<TxUnmodifiableSet> columns) {
        var frames = makeFrames(columns, math);
        return new FrameSet(frames, new FrameMapper(frames), math);
    }

    public static FrameSet create(FactorProvider math, ArrayList<TxUnmodifiableSet> columns, FrameSet startingPoint) {
        var frames = makeFrames(columns, math);
        return new FrameSet(frames, startingPoint.candidateMapper, math);
    }

    public boolean canPromote(int n) {
        int frameNum = candidateMapper.homeFrame(n);
        return frames[frameNum-1].canPromoteInto(n, math, candidateMapper) &&
                frames[frameNum].canPromoteOutOf(n, math, candidateMapper);
    }

    public FrameSet promote(int n) {
        int frameNum = candidateMapper.homeFrame(n);
        var into = frames[frameNum-1].promoteInto(n, math, candidateMapper);
        if (into == null) return null;
        var outOf = frames[frameNum].promoteOutOf(n, math, candidateMapper);
        if (outOf == null) return null;
        var newFrames = new Frame[frames.length];
        System.arraycopy(frames, 0, newFrames, 0, frames.length);
        newFrames[frameNum-1] = into;
        newFrames[frameNum] = outOf;
        return new FrameSet(newFrames, candidateMapper, math);
    }

    public void debugDump() {
        System.out.println(this);
    }

    public String toString(String prefix) {
        return Arrays.stream(frames)
                .map(frame -> String.format("%s%s", prefix, frame))
                .collect(Collectors.joining("\n"));
    }

    public String toString() {
        return toString("");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FrameSet)) return false;
        if (this == obj) return true;
        FrameSet other = (FrameSet) obj;
        return Arrays.equals(frames, other.frames);
    }

    public List<Frame> getFrames() {
        return Arrays.asList(frames);
    }

    /**
     * In order to be a candidate, a factor has to have the freedom to move out of its frame,
     * and the receiving frame has to have the freedom to accept it.
     */
    public TxSet narrowCandidateSet(TxSet widerCandidateSet) {
        TxSet result = TxSet.empty();
        for (int i = 1; i < frames.length; i++) {
            var potentialCandidates = frames[i].getFactors();
            if (potentialCandidates.size() > 0) {
                if (frames[i].freedoms() > 0 && frames[i-1].freedoms() > 0) {
                    result.appendAll(potentialCandidates);
                }
            }
        }
        result = TxSet.and(result, widerCandidateSet);
        return result;
    }

    public int computeMaxNumberOfPromotions() {
        return computeMaxNumberOfPromotions(null);
    }

    public int computeMaxNumberOfPromotions(TxSet candidates) {
        int result = 0;
        int upstreamOffer = 0;
        for (var frame: getFrames()) {
            var freedoms = frame.freedoms();
            var numPossibleToPromoteOut = freedoms;
            if (candidates != null) {
                numPossibleToPromoteOut = Math.min(freedoms, TxSet.and(candidates, frame.getFactors()).size());
            }
            var bestCasePromotionCount = Math.min(numPossibleToPromoteOut, upstreamOffer);
            result += bestCasePromotionCount;
            upstreamOffer = freedoms - bestCasePromotionCount;
        }
        assert result >= 0;
        return result;
    }

    public void debugDumpFactors() {
        for (var frame: frames) {
            System.out.println(frame + ":");
            frame.debugDumpFactors(math);
        }
    }

    public boolean containsFactor(int n) {
        return frames[candidateMapper.homeFrame(n)].getFactors().contains(n);
    }

    public int mapToFrame(int n) {
        return candidateMapper.homeFrame(n);
    }

    public FrameSet memoize() {
        if (isMemoized()) return this;
        var memFrames = Arrays.stream(frames).map(MemoizedFrame::new).toArray(Frame[]::new);
        return new FrameSet(memFrames, candidateMapper, math);
    }

    public FrameSet dememoize() {
        if (!isMemoized()) return this;
        var memFrames = Arrays.stream(frames).map(Frame::new).toArray(Frame[]::new);
        return new FrameSet(memFrames, candidateMapper, math);
    }

    private boolean isMemoized() {
        return frames.length == 0 || frames[0] instanceof MemoizedFrame;
    }

    public FrameSet setFrameZeroFreedoms(int frameZeroFreedoms) {
        var newFrames = new Frame[frames.length];
        System.arraycopy(frames, 0, newFrames, 0, frames.length);
        var f0 = new Frame(0, frames[0].getMoves(), frames[0].getFactors(), frameZeroFreedoms);
        if (isMemoized()) f0 = new MemoizedFrame(f0);
        newFrames[0] = f0;
        return new FrameSet(newFrames, candidateMapper, math);
    }


    public void debugPromotionEstimate() {
        debugPromotionEstimate(null);
    }

    public void debugPromotionEstimate(TxSet solution) {
        System.out.print("   frame #:");
        for (int i = frames.length-1; i >= 0; i--) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\n  freedoms:");
        for (int i = frames.length-1; i >= 0; i--) {
            System.out.printf(" %2d", frames[i].freedoms());
        }

        var promotions = new int[frames.length];
        int promotionTotal = 0;
        int upstreamOffer = 0;
        for (int i = 0; i < frames.length; i++) {
            var frame = frames[i];
            var freedoms = frame.freedoms();
            var bestCasePromotionCount = Math.min(freedoms, upstreamOffer);
            promotions[i] = bestCasePromotionCount;
            promotionTotal += bestCasePromotionCount;
            upstreamOffer = freedoms - bestCasePromotionCount;
        }

        System.out.print("\npromotions:");
        for (int i = frames.length-1; i >= 0; i--) {
            System.out.printf(" %2d", promotions[i]);
        }


        if (solution != null) {
            System.out.print("\n  solution:");
            for (int i = frames.length-1; i >= 0; i--) {
                int finalI = i;
                var count = solution.stream()
                        .filter(n->mapToFrame(n) == finalI)
                        .count();
                System.out.printf(" %2d", count);
            }
            System.out.printf("\nestimated max promotions in total: %d, actual: %d\n", promotionTotal, solution.size());
        } else {
            System.out.printf("\nestimated max promotions in total: %d\n", promotionTotal);
        }
    }
}