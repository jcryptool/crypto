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


import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * List of integers stored in an array.  Could use ArrayList, but then you get Integer objects rather than
 * just ints.
 */
public class TxList extends TxCollection {
    public static final int DEFAULT_SIZE = 10;

    private int[] arr;
    private int elementCount;

    public static TxList of(int n) {
        TxList l = new TxList();
        l.append(n);
        return l;
    }

    public static TxList of(int... values) {
        return new TxList(values.clone(), values.length);
    }

    public static TxList of(IntStream stream) {
        int[] arr = stream.toArray();
        return TxList.of(arr);
    }

    public static TxList of(TxCollection c) {
        return TxList.of(c.toArray());
    }

    public static TxList concat(TxList l1, TxList l2) {
        TxList ret = new TxList(l1.elementCount + l2.elementCount);
        ret.append(l1);
        ret.append(l2);
        return ret;
    }

    public static TxList concat(TxList l1, int n) {
        TxList ret = new TxList(l1.elementCount + DEFAULT_SIZE);
        ret.append(l1);
        ret.append(n);
        return ret;
    }

    public static TxList concat(int n, TxList l1)  {
        TxList ret = new TxList(l1.elementCount + DEFAULT_SIZE);
        ret.append(n);
        ret.append(l1);
        return ret;
    }

    public static TxList concat(TxList l1, int... values) {
        TxList ret = new TxList(l1.elementCount + values.length);
        ret.append(l1);
        ret.append(values);
        return ret;
    }

    public static TxList concat(TxList... lists) {
        return TxList.of(Stream.of(lists).flatMapToInt(TxList::stream));
    }

    public static TxList concat(Stream<TxList> lists) {
        return concat(lists.toArray(TxList[]::new));
    }

    public static TxList concat(TxList l1, IntStream stream) {
        return concat(l1, of(stream));
    }

    public TxList() {
        this(DEFAULT_SIZE);
    }

    public TxList(int initialSize) {
        arr = new int[initialSize];
        elementCount = 0;
    }

    TxList(int[] arr, int count) {
        this.arr = arr;
        elementCount = count;
    }

    public static TxList empty() {
        return new TxList();
    }

    public void append(int n) {
        if (elementCount == arr.length) {
            int[] newArr = new int[arr.length*2+1];
            System.arraycopy(arr, 0, newArr, 0, arr.length);
            arr = newArr;
        }
        arr[elementCount++] = n;
    }

    public void append(TxCollection other) {
        other.forEach(this::append);
    }

    public void append(int ... values) {
        appendSome(values, values.length);
    }


    private void appendSome(int[] values, int length) {
        if (elementCount + length > arr.length) {
            int[] newArr = new int[elementCount + length + DEFAULT_SIZE];  // leave some room to grow
            System.arraycopy(arr, 0, newArr, 0, arr.length);
            arr = newArr;
        }
        System.arraycopy(values, 0, arr, elementCount, length);
        elementCount += length;
    }

    public int length() {
        return elementCount;
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    public IntStream reverse() {
        int[] rev = new int[elementCount];
        for (int i = 0; i < elementCount; i++) {
            rev[elementCount - i - 1] = arr[i];
        }
        return Arrays.stream(rev);
    }

    public int pop() {
        if (elementCount == 0)
            throw new EmptyStackException();
        return arr[--elementCount];
    }

    public int peek() {
        if (elementCount == 0)
            throw new EmptyStackException();
        return arr[elementCount-1];
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.elementCount; i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    public int[] toArray() {
        int[] ret = new int[elementCount];
        System.arraycopy(arr, 0, ret, 0, elementCount);
        return ret;
    }

    @Override
    public IntStream stream() {
        return Arrays.stream(arr).limit(elementCount);
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public int max() {
        return stream().max().orElse(-1);
    }

    @Override
    public int min() {
        return stream().min().orElse(-1);
    }

    @Override
    public boolean contains(int n) {
        return stream().anyMatch(x -> x == n);
    }

    public PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TxList))
            return false;
        var other = (TxList) obj;
        if (this == obj)
            return true;
        if (other.elementCount != elementCount)
            return false;
        for (int i = 0; i < elementCount; i++) {
            if (arr[i] != other.arr[i])
                return false;
        }
        return true;
    }

    public int hashCode() {
        int res = 1;
        for (int i = 0; i < elementCount; i++) {
            res = 31 * res + arr[i];
        }
        return res;
    }

    @Override
    public int compareTo(TxCollection o) {
        return 0;
    }

    public TxUnmodifiableList unmodifiable() {
        return TxUnmodifiableList.of(this);
    }

}