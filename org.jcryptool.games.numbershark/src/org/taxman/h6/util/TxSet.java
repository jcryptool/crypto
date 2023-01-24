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
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.*;

public class TxSet extends TxCollection {
    public static final int MAX_VALUE = 1023;

    public final BitSet bits;

    static BitSet cloneBits(BitSet bits) {
        return (BitSet) bits.clone();
    }

    public static TxSet of(TxCollection c) {
        return TxSet.of(c.stream());
    }

    public static TxSet of(TxSet s) {
        return new TxSet(cloneBits(s.bits));
    }

    public static TxSet of(IntStream stream) {
        return TxSet.of(stream.toArray());
    }

    public static TxSet of(int... arr) {
        return new TxSet(intsToBitSet(arr));
    }

    public static BitSet intsToBitSet(int... arr) {
        int max = 0;
        for (int i : arr) max = Math.max(i, max);
        BitSet bits = new BitSet(max+1);
        for (int i : arr) {
            assert i > 0 && i <= MAX_VALUE : "cannot insert " + i + " with max value of " + MAX_VALUE;
            bits.set(i);
        }
        return bits;
    }

    public static TxSet of(Stream<TxSet> s) {
        return TxSet.or(s.toArray(TxSet[]::new));
    }

    public static TxSet of(Collection<Integer> c) {
        return TxSet.of(c.stream().mapToInt(Integer::intValue));
    }

    public static TxSet of(BitSet bits) {
        return new TxSet(cloneBits(bits));
    }

    public static TxSet empty() {
        return new TxSet(new BitSet(0));
    }

    TxSet(BitSet b) {
        bits = b;
    }

    TxSet(int... arr) {
        this(intsToBitSet(arr));
    }

    @Override
    public int[] toArray() {
        int sz = size();
        var result = new int[sz];
        int prev = 0;
        for (int i=0; i < sz; i++) {
            prev = bits.nextSetBit(prev+1);
            result[i] = prev;
        }
        return result;
    }

    public int[] descendingArray() {
        var arr = new int[size()];
        int i=0;
        for (int j = bits.length(); (j = bits.previousSetBit(j-1)) >= 0; ) {
            arr[i++] = j;
        }
        return arr;
    }

    @Override
    public IntStream streamDescending() {
        return Arrays.stream(descendingArray());
    }

    @Override
    public int sum() {
        int result = 0;
        for (int i = bits.nextSetBit(1); i >= 0; i = bits.nextSetBit(i+1))
            result += i;
        return result;
    }

    @Override
    public IntStream stream() {
        return bits.stream();
    }

    @Override
    public int size() {
        return bits.cardinality();
    }

    @Override
    public int max() {
        return bits.previousSetBit(bits.length()-1);
    }

    @Override
    public int min() {
        return bits.nextSetBit(0);
    }

    @Override
    public boolean contains(int n) {
        return bits.get(n);
    }

    public boolean contains(TxSet other) {
        for (int n = other.max(); n > 0; n = other.nextHighest(n)) {
            if (!this.contains(n)) return false;
        }
        return true;
    }

    public static TxSet and(TxSet s1, TxSet s2) {
        var newBits = TxSet.cloneBits(s1.bits);
        newBits.and(s2.bits);
        return new TxSet(newBits);
    }

    public static TxSet and(TxSet... sets) {
        if (sets.length == 0) return empty();
        var newBits = TxSet.cloneBits(sets[0].bits);
        for (int i = 1; i < sets.length; i++) {
            newBits.and(sets[i].bits);
        }
        return new TxSet(newBits);
    }

    public static TxSet and(TxSet first, int n) {
        if (first.bits.get(n)) {
            return TxSet.of(n);
        } else {
            return TxSet.empty();
        }
    }

    public static TxSet or(TxSet... sets) {
        if (sets.length == 0) {
            return empty();
        } else {
            TxSet result = TxSet.of(sets[0]);
            for (int i = 1; i < sets.length; i++) {
                result.bits.or(sets[i].bits);
            }
            return result;
        }
    }

    public static TxSet or(TxSet first, int n) {
        BitSet newBits = cloneBits(first.bits);
        newBits.set(n);
        return new TxSet(newBits);
    }

    public static TxSet subtract(TxSet first, TxSet second) {
        BitSet newBits = cloneBits(first.bits);
        newBits.andNot(second.bits);
        return new TxSet(newBits);
    }

    public static TxSet subtract(TxSet first, int n) {
        BitSet newBits = cloneBits(first.bits);
        newBits.set(n, false);
        return new TxSet(newBits);
    }

    public static Comparator<TxSet> BySumDescending() {
        return Comparator.comparing(TxSet::sum).reversed();
    }

    @Override
    public void append(int n) {
        assert n >= 0 && n <= MAX_VALUE;
        bits.set(n);
    }

    public void appendAll(TxSet other) {
        this.bits.or(other.bits);
    }

    @Override
    public int compareTo(TxCollection o) {
        return toString().compareTo(o.toString());
    }

    public boolean isEmpty() {
        return bits.isEmpty();
    }

    public TxUnmodifiableSet unmodifiable() {
        return TxUnmodifiableSet.of(this);
    }

    @Override
    public String toString() {
        return toString("{", "}");
    }

    public String toString(String start, String end) {
        String meat = streamDescending()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        return start + meat + end;
    }

    public String toStringNoBrackets() {
        return toString("", "");
    }

    public boolean equals(Object obj) {
        if (obj instanceof TxSet) {
            var other = (TxSet) obj;
            return this == other || bits.equals(other.bits);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return bits.hashCode();
    }

    @Override
    public IntStream sorted() {
        return stream();
    }

    public static Collector<TxCollection, TxSet, TxSet> collector() {
        return Collector.of(TxSet::empty,
                TxSet::appendAll,
                TxSet::or,
                Collector.Characteristics.IDENTITY_FINISH);
    }

    public TxSet largest(int sz) {
        if (sz >= size()) return TxSet.of(this);
        int j = bits.length();
        var newBits = new BitSet(j);
        for (int i = 0; i < sz; i++) {
            j = bits.previousSetBit(j-1);
            newBits.set(j);
        }
        return new TxSet(newBits);
    }

    public TxSet smallest(int sz) {
        int fullSize = size();
        if (sz >= fullSize) return TxSet.of(this);
        int j = 0;
        var newBits = new BitSet(fullSize);
        for (int i=0; i < sz; i++) {
            j = bits.nextSetBit(j+1);
            newBits.set(j);
        }
        return new TxSet(newBits);
    }

    public int sumOfLargest(int sz) {
        if (sz >= size()) return sum();
        int j = bits.length();
        int result = 0;
        for (int i = 0; i < sz; i++) {
            j = bits.previousSetBit(j-1);
            result += j;
        }
        return result;
    }

    public void remove(int n) {
        assert n >= 0 && n <= MAX_VALUE;
        bits.set(n, false);
    }

    public void removeAll(TxSet other) {
        bits.andNot(other.bits);
    }

    public int nextHighest(int n) {
        int result = bits.previousSetBit(n-1);
        assert result < n;
        return result;
    }

    public byte[] toBytes() {
        var arr = descendingArray();
        ByteBuffer bb = ByteBuffer.allocate(arr.length*2 + 2);
        bb.putChar((char) arr.length);
        for (int n: arr) bb.putChar((char) n);
        return bb.array();
    }

    public static TxSet read(InputStream is) throws IOException {
        int length = readShort(is);
        if (length < 0) return null;
        var bits = new BitSet();
        for (int i = 0; i < length; i++) bits.set(readShort(is));
        return new TxSet(bits);
    }

    public static TxSet readFromBigArray(byte[] bigArray, int start) {
        int length = readShortFromBigArray(bigArray, start);
        if (length < 0) return null;
        var bits = new BitSet();
        for (int i = start+2; i < start+1+length*2; i += 2) {
            bits.set(readShortFromBigArray(bigArray, i));
        }
        return new TxSet(bits);
    }

    static int readShortFromBigArray(byte[] bigArray, int position) {
        int first = bigArray[position];
        if (first == -1) return -1;
        int second = bigArray[position+1] & 0xFF;
        return (first << 8) + second;
    }

    protected static int readShort(InputStream is) throws IOException {
        int first = is.read();
        if (first == -1) return -1;
        int second = is.read();
        return (first << 8) + second;
    }

    public BitSet toBits() {
        return cloneBits(bits);
    }

    /**
     * Return a stream of combinations from the numbers set of all sizes equal to the size parameter or smaller
     */
    public static Stream<TxSet> combinationsUpToSize(TxSet numbers, int size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(x -> combinations(numbers, x))
                .flatMap(x -> x);
    }

    /**
     * Return a stream of all combinations of numbers from the numbers set up to the size given
     * by the size parameter.
     */
    public static Stream<TxSet> combinations(TxSet numbers, int size) {
        if (numbers.size() < size) {
            return Stream.of();  // empty
        } else if (numbers.size() == size) {
            return Stream.of(numbers);
        } else if (size == 1) {
            return numbers.stream()
                    .mapToObj(TxSet::of);
        } else {
            return numbers.stream()
                    .mapToObj(n -> combinations(TxSet.of(numbers.filter(x -> x < n)), size-1)
                            .map(set -> TxSet.or(set, n))
                    )
                    .flatMap(x -> x);
        }
    }
}
