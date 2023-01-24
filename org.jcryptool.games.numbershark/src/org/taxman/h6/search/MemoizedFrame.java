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

import org.taxman.h6.util.TxUnmodifiableSet;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;


public class MemoizedFrame extends Frame {
    private volatile MemoizedFrame[] memoTable;
    private static final RecycleRef[] recycleRing = new RecycleRef[(int)(Runtime.getRuntime().maxMemory() / (1 << 17))];
    private static final AtomicLong ringPosition = new AtomicLong();
    private static final MemoizedFrame[] EMPTY_TABLE = new MemoizedFrame[0];
    protected static final MemoizedFrame PROMOTION_FAIL = new MemoizedFrame(-1, null, null, 0);

    MemoizedFrame(int frameNumber, TxUnmodifiableSet moves, TxUnmodifiableSet factors,
                  MemoizedFrame[] memoTable, int freedoms) {
        super(frameNumber, moves, factors, freedoms);
        this.memoTable = memoTable;
        if (frameNumber >= 0) register();
    }


    protected MemoizedFrame(int frameNumber, TxUnmodifiableSet moves, TxUnmodifiableSet factors, int freedoms) {
        this(frameNumber, moves, factors, null, freedoms);
    }

    protected MemoizedFrame(Frame f) {
        this(f.frameNumber, f.getMoves(), f.getFactors(), f.freedoms());
    }

    @Override
    public Frame promoteInto(int n, FactorProvider math, FrameMapper mapper) {
        var mappedLocationForN = mapper.slotForNumberWithinFrame(frameNumber, n);
        var result = getMemo(mappedLocationForN);
        if (result == null) {
            result = setMemo(super.promoteInto(n, math, mapper), mappedLocationForN);
        } else if (result == PROMOTION_FAIL) {
           result = null;
        }
        return result;
    }

    @Override
    public Frame promoteOutOf(int n, FactorProvider math, FrameMapper mapper) {
        var mappedLocationForN = mapper.slotForNumberWithinFrame(frameNumber, n);
        var result = getMemo(mappedLocationForN);
        if (result == null) {
            result = setMemo(super.promoteOutOf(n, math, mapper), mappedLocationForN);
        } else if (result == PROMOTION_FAIL) {
            result = null;
        }
        return result;
    }

    @Override
    public boolean canPromoteInto(int n, FactorProvider math, FrameMapper mapper) {
        return promoteInto(n, math, mapper) != null;  // doing it is faster because we get to memoize the result
    }

    @Override
    public boolean canPromoteOutOf(int n, FactorProvider math, FrameMapper mapper) {
        return promoteOutOf(n, math, mapper) != null;  // doing it is faster because we get to memoize the result
    }

    private MemoizedFrame setMemo(Frame f, int location) {
        MemoizedFrame result;
        if (f != null) {
            result = new MemoizedFrame(f);
            getMemoTable(location)[location] = result;
        } else {
            result = null;
            getMemoTable(location)[location] = PROMOTION_FAIL;
        }
        return result;
    }

    private MemoizedFrame getMemo(int location) {
        return getMemoTable(location)[location];
    }

    private MemoizedFrame[] getMemoTable(int location) {
        var result = memoTable;
        if (result == null || result.length <= location) {
            synchronized (this) {
                result = memoTable;
                if (result == null) {
                    result = new MemoizedFrame[location + 1];
                } else if (result.length <= location) {
                    var old = result;
                    result = new MemoizedFrame[location + 1];
                    System.arraycopy(old, 0, result, 0, old.length);
                }
                memoTable = result;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof MemoizedFrame)) return false;
        if (!super.equals(obj)) return false;
        MemoizedFrame other = (MemoizedFrame) obj;
        if (memoTable == null ^ other.memoTable == null) return false;
        return Arrays.equals(memoTable, other.memoTable);
    }

    private void register() {
        var pos = (int) (ringPosition.getAndIncrement() % recycleRing.length);
        var ref = recycleRing[pos];
        if (ref != null) ref.clear();
        recycleRing[pos] = new RecycleRef(this);
    }

    private static class RecycleRef extends WeakReference<MemoizedFrame> {

        public RecycleRef(MemoizedFrame referent) {
            super(referent);
        }

        public void clear() {
            var referent = get();
            if (referent != null) {
                referent.memoTable = EMPTY_TABLE;  // avoid null pointer exceptions this way
            }
        }
    }
}