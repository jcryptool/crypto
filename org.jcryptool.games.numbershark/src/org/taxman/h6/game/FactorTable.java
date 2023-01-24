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

package org.taxman.h6.game;

import org.taxman.h6.util.TxList;
import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import java.io.PrintStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.stream.Stream;

public class FactorTable {
    private final int highest;
    private final TxSet[] factors;
    private final TxSet[] composites;
    private final TxSet[] abbreviatedFactors;
    private final TxSet[] abbreviatedComposites;
    public final TxSet primes;
    public final TxSet numbers;
    public final TxList[] primeFactors;

    private static class ScratchSet extends BitSet {
        public final int n;

        ScratchSet(int n) {
            this.n = n;
        }

        void syncAdd(int i) {
            synchronized (this) {
                set(i);
            }
        }
    }

    private class ScratchFactorTable {
        private final ScratchSet[] arr;

        ScratchFactorTable() {
            this.arr = IntStream.rangeClosed(0, FactorTable.this.highest)
                    .mapToObj(ScratchSet::new)
                    .toArray(ScratchSet[]::new);

        }

        public Stream<ScratchSet> stream() {
            return Arrays.stream(arr).skip(1);  // don't return arr[0]
        }

        public void append(int i, int n) {
            arr[i].syncAdd(n);
        }

        public void set(ScratchSet ss) {
            arr[ss.n] = ss;
        }

        public ScratchSet get(int i) {
            return arr[i];
        }

        public TxSet[] toTxSetArray() {
            return Arrays.stream(arr)
                    .map(bits -> TxSet.of(bits).unmodifiable())
                    .toArray(TxSet[]::new);
        }
    }

    public FactorTable(int highest) {
        this(TxSet.of(IntStream.rangeClosed(1, highest)));
    }

    public FactorTable(TxSet numbers) {
        this.numbers = numbers;
        if (numbers.size() == 0) {
            highest = 0;
            factors = new TxSet[0];
            composites = new TxSet[0];
            abbreviatedFactors = new TxSet[0];
            abbreviatedComposites = new TxSet[0];
            primes = TxUnmodifiableSet.EmptySet;
            primeFactors = new TxList[0];
            return;
        }

        highest = numbers.max();
        final ScratchFactorTable scratchFactors = new ScratchFactorTable();
        final ScratchFactorTable scratchComposites = new ScratchFactorTable();
        final ScratchFactorTable scratchAbbreviatedFactors = new ScratchFactorTable();
        final ScratchFactorTable scratchAbbreviatedComposites = new ScratchFactorTable();

        // generate factors (excluding 1 and the number itself)
        IntStream.rangeClosed(2, highest)
                .parallel()
                .forEach(n -> {
                    for (int x = 2; x <= Math.sqrt(n); x++) {
                        if (n % x == 0) {
                            int f2 = n / x;
                            //System.out.format("%d  = %d x %d\n", n, x, f2);
                            scratchFactors.append(n, x);
                            scratchFactors.append(n, f2);
                            scratchComposites.append(x, n);
                            scratchComposites.append(f2, n);
                        }
                    }
                });

        BitSet numbersAsBits = new BitSet(highest);
        numbers.forEach(numbersAsBits::set);
        boolean numbersContainsOne = numbers.contains(1);

        // Generate abbreviated factor table by taking a numbers factors and filtering out any
        // that are also factors of one of the factors.  Patch up the fact that we previously excluded 1
        // by adding 1 into any number that doesn't have any abbreviated factors (1 and the primes.)
        numbers.stream()
                .parallel()
                .mapToObj(scratchFactors::get)
                .forEach(ss -> {
                    BitSet subFactors = new BitSet();
                    ss
                            .stream()
                            .filter(numbers::contains)
                            .flatMap(f -> scratchFactors.get(f).stream())
                            .filter(numbers::contains)
                            .forEach(subFactors::set);
                    ScratchSet abbrev = (ScratchSet) ss.clone();
                    abbrev.and(numbersAsBits);
                    abbrev.andNot(subFactors);
                    if (ss.n > 1 && abbrev.cardinality() == 0 && numbersContainsOne)
                        abbrev.set(1);
                    scratchAbbreviatedFactors.set(abbrev);
                });

        // Generate abbreviated composites by inverting the abbreviated factor table
        scratchAbbreviatedFactors
                .stream()
                .forEach(ss -> ss.stream().forEach(f -> {
                    if (ss.n > 1)
                        scratchAbbreviatedComposites.get(f).syncAdd(ss.n);
                }));

        // Add 1 and the number itself into the factor table
        scratchFactors
                .stream()
                .forEach(ss -> {
                    ss.set(1);
                    ss.set(ss.n);
                });

        // store factor and composite tables, then make a list of the primes
        factors = scratchFactors.toTxSetArray();
        composites = scratchComposites.toTxSetArray();
        abbreviatedFactors = scratchAbbreviatedFactors.toTxSetArray();
        abbreviatedComposites = scratchAbbreviatedComposites.toTxSetArray();
        primes = TxSet.of(getRange().filter(n -> scratchFactors.get(n).cardinality() == 2)).unmodifiable();
        primeFactors = buildPrimeFactorTable();
    }

    public int getHighest() {
        return highest;
    }

    public IntStream getRange() {
        return IntStream.rangeClosed(1, getHighest());
    }

    public TxSet getFactors(int n) {
        assert n > 0 && n <= highest;
        return factors[n];
    }

    public TxSet getComposites(int n) {
        assert n > 0 && n <= highest;
        return composites[n];
    }

    public TxSet getAbbreviatedFactors(int n) {
        assert n > 0 && n <= highest;
        return abbreviatedFactors[n];
    }

    public TxSet getAbbreviatedComposites(int n) {
        assert n > 0 && n <= highest;
        return abbreviatedComposites[n];
    }

    public boolean isPrime(int n) {
        return primes.contains(n);
    }

    private TxList computePrimeFactors(int n, TxList[] table) {
        if (n == 1) {
            return TxList.empty();
        } else if (isPrime(n)) {
            return TxList.of(n);
        } else {
            int largest = TxSet.subtract(getFactors(n), n).max();
            return TxList.concat(TxList.of(table[largest]), table[n/largest]);
        }
    }

    public TxList[] buildPrimeFactorTable() {
        var table = new TxList[highest+1];
        for (int i = 1; i <= highest; i++) {
            table[i] = computePrimeFactors(i, table);
        }
        return table;
    }

    public void printTables(PrintStream out) {
        final BiConsumer<String, Function<Integer, TxSet>> pe = (str, f) -> {
            out.println(str);
            getRange().forEach(n -> {
                TxSet set = f.apply(n);
                if (!set.isEmpty()) {
                    out.format("%4d: %s\n", n, set);
                }
            });
        };

        pe.accept("factors", this::getFactors);
        pe.accept("\ncomposites", this::getComposites);
        pe.accept("\nabbreviated factors", this::getAbbreviatedFactors);
        pe.accept("\nabbreviated composites", this::getAbbreviatedComposites);
        out.format("\nprimes: %s\n", primes.toString());
    }

    public static void main(String[] args, boolean print) {
        int highest = Integer.parseInt(args[0]);
        FactorTable fm = new FactorTable(highest);
        if (print && highest < 200)
            fm.printTables(System.out);
    }
}