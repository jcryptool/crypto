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

import org.taxman.h6.util.TxSet;

public class Move {
    public final int n;
    public final Board before;
    public final Board remainder;
    public final TxSet tax;

    public Move(int n, Board b) throws VerificationException {
        if (!b.set.contains(n))
            throw new VerificationException("illegal move: "+n+", is not on the board: " + b, n);
        this.n = n;
        before = b;
        TxSet toRemove = b.allFactors(n);
        tax = TxSet.subtract(toRemove, n).unmodifiable();
        if (tax.size() == 0)
            throw new VerificationException("illegal move: "+n+", no tax paid", n);
        remainder = b.subtract(toRemove);
    }
}
