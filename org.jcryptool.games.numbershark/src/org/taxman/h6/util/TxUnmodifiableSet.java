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

package org.taxman.h6.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TxUnmodifiableSet extends TxSet {
    public static final TxUnmodifiableSet EmptySet = of(TxSet.empty());
    public static final TxUnmodifiableSet[] singletons = IntStream.range(1, 1024)
            .mapToObj(TxUnmodifiableSet::new)
            .toArray(TxUnmodifiableSet[]::new);

    private TxUnmodifiableSet(TxSet other) {
        super(TxSet.cloneBits(other.bits));
    }

    private TxUnmodifiableSet(int... arr) {
        super(arr);
    }

    protected TxUnmodifiableSet(BitSet trusted) {
        super(trusted);
    }

    public static TxUnmodifiableSet of(TxSet other) {
        if (other instanceof TxUnmodifiableSet) {
            return (TxUnmodifiableSet) other;
        } else {
            TxUnmodifiableSet result;
            if (other.size() == 1 && other.max() < singletons.length) {
                result = singletons[other.max()-1];
            } else {
                result = new TxUnmodifiableSet(other);
            }
            return result;
        }
    }

    public static TxUnmodifiableSet of(int... arr) {
        return new TxUnmodifiableSet(arr);
    }

    public static TxUnmodifiableSet of(long[] longs) {
        var bits = BitSet.valueOf(longs);
        return new TxUnmodifiableSet(bits);
    }

    public static TxUnmodifiableSet or(TxUnmodifiableSet set, int n) {
        var newBits = TxSet.cloneBits(set.bits);
        newBits.set(n);
        return new TxUnmodifiableSet(newBits);
    }

    public static TxUnmodifiableSet subtract(TxSet s1, TxSet s2) {
        var newBits = TxSet.cloneBits(s1.bits);
        newBits.andNot(s2.bits);
        return new TxUnmodifiableSet(newBits);
    }

    public static TxUnmodifiableSet subtract(TxSet first, int n) {
        BitSet newBits = cloneBits(first.bits);
        newBits.set(n, false);
        return new TxUnmodifiableSet(newBits);
    }

    public static TxUnmodifiableSet and(TxSet s1, TxSet s2) {
        var newBits = TxSet.cloneBits(s1.bits);
        newBits.and(s2.bits);
        return new TxUnmodifiableSet(newBits);
    }

    public static TxUnmodifiableSet and(TxSet... sets) {
        if (sets.length == 0) return EmptySet;
        var newBits = TxSet.cloneBits(sets[0].bits);
        for (int i = 1; i < sets.length; i++) {
            newBits.and(sets[i].bits);
        }
        return new TxUnmodifiableSet(newBits);
    }

    public static TxUnmodifiableSet or(TxSet... sets) {
        if (sets.length == 0) {
            return EmptySet;
        } else {
            BitSet resultBits = TxSet.cloneBits(sets[0].bits);
            for (int i = 1; i < sets.length; i++) {
                resultBits.or(sets[i].bits);
            }
            return new TxUnmodifiableSet(resultBits);
        }
    }

    public static TxUnmodifiableSet of(Stream<TxSet> s) {
        BitSet bits = new BitSet();
        s.forEach(set -> bits.or(set.bits));
        return new TxUnmodifiableSet(bits);
    }

    public static TxUnmodifiableSet of(IntStream stream) {
        return TxUnmodifiableSet.of(stream.toArray());
    }

    public static TxUnmodifiableSet fromLongArray(long[] data) {
        return new TxUnmodifiableSet(BitSet.valueOf(data));
    }


    @Override
    public void append(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TxUnmodifiableSet unmodifiable() {
        return this;
    }

    public static TxUnmodifiableSet read(InputStream is) throws IOException {
        int length = readShort(is);
        if (length < 0) return null;
        var bits = new BitSet();
        for (int i = 0; i < length; i++) bits.set(readShort(is));
        return new TxUnmodifiableSet(bits);
    }

    public static TxUnmodifiableSet readFromBigArray(byte[] bigArray, int start) {
        int length = TxSet.readShortFromBigArray(bigArray, start);
        if (length < 0) return null;
        var bits = new BitSet();
        //var tiny = new byte[length*2+2];  //todo: remove
        //System.arraycopy(bigArray, start, tiny, 0, tiny.length); //todo: remove
        for (int i = start+2; i < start+2+length*2; i += 2) {
            int n = TxSet.readShortFromBigArray(bigArray, i);
            //assert n > 0; //todo: remove
            bits.set(n);
        }
        return new TxUnmodifiableSet(bits);
    }

}
