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

import org.taxman.h6.game.Board;
import org.taxman.h6.util.TxSet;

public class FactorProvider {
    private final int[][] factorTable;
    private final int[][] compositeTable;

    private FactorProvider(int[][] factorTable, int[][] compositeTable) {
        this.factorTable = factorTable;
        this.compositeTable = compositeTable;
    }

    static FactorProvider create(Board b, TxSet allRemainingNumbers) {
        int max = allRemainingNumbers.max();
        int[][] factors = new int[max+1][];
        int[][] composites = new int[max+1][];
        allRemainingNumbers.stream().forEach(n -> {
            factors[n] = TxSet.and(b.factors(n), allRemainingNumbers).toArray();
            composites[n] = TxSet.and(b.composites(n), allRemainingNumbers).toArray();
        });
        return new FactorProvider(factors, composites);
    }

    public int[] getFactors(int n) {
        return factorTable[n];
    }

    public int[] getComposites(int n) {
        return compositeTable[n];
    }
}
