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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class FrameMapper {
    public final AtomicLong promotionCounter;  // doesn't particularly belong here, just a convenient place to put it
    private final int[] homeFrameForNumber;
    private final int[][] slotWithinFrame;

    public FrameMapper(Frame[] frames) {
        this.promotionCounter = new AtomicLong(0);
        int maxNumber = Arrays.stream(frames)
                .map(Frame::allNumbers)
                .flatMapToInt(TxSet::stream)
                .max()
                .orElse(0);
        this.slotWithinFrame = new int[frames.length][maxNumber+1];
        int[] slotCount = new int[frames.length];
        this.homeFrameForNumber = new int[maxNumber + 1];
        for (int i = 0; i < frames.length; i++) {
            for (int n : frames[i].getFactors().toArray()) {
                homeFrameForNumber[n] = i;
                slotWithinFrame[i][n] = slotCount[i]++;
            }
            if (i > 0) {
                for (int m : frames[i].getFactors().toArray()) {
                    slotWithinFrame[i-1][m] = slotCount[i-1]++;
                }
            }
        }
    }

    public int homeFrame(int n) {
        return homeFrameForNumber[n];
    }

    public int slotForNumberWithinFrame(int frameNumber, int n) {
        return this.slotWithinFrame[frameNumber][n];
    }
}