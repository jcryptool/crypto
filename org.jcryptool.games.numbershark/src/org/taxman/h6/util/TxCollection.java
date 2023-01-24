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

import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public abstract class TxCollection implements Comparable<TxCollection> {

    public IntStream sorted() {
        return stream().sorted();
    }

    public IntStream streamDescending() {
        int[] arr = sorted().toArray();
        return IntStream.rangeClosed(1, arr.length)
                .map(i -> arr[arr.length - i]);
    }

    public PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }

    public int[] toArray() {
        return stream().toArray();
    }

    public void appendAll(TxCollection c) {
        c.forEach(this::append);
    }

    public void appendAll(int... c) {
        for (int i: c)
            append(i);
    }

    public void appendAll(IntStream s) {
        s.forEach(this::append);
    }

    public void forEach(IntConsumer func) {
        stream().forEach(func);
    }

    public IntStream map(IntUnaryOperator func) {
        return stream().map(func);
    }

    public IntStream filter(IntPredicate p) {
        return stream().filter(p);
    }

    public int sum() {
        return stream().sum();
    }

    public abstract IntStream stream();

    public abstract int size();

    public abstract int max();

    public abstract int min();

    public abstract boolean contains(int n);

    public abstract void append(int n);

}

