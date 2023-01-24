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

import java.util.BitSet;

public class TxCompressor {
    private int[] mapIn;
    private int[] mapOut;
    private int numMapped;

    public static final int DEFAULT_SIZE = 10;

    public TxCompressor() {
        this.mapIn = new int[DEFAULT_SIZE];
        this.mapOut = new int[DEFAULT_SIZE];
        this.numMapped = 0;
    }

    public TxSet compress(TxSet toCompress) {
        BitSet bits = new BitSet();
        toCompress.stream().map(this::compressNumber).forEach(bits::set);
        return TxSet.of(bits);
    }

    public TxSet decompress(TxSet toDecompress) {
        return TxSet.of(toDecompress.stream().map(this::decompressNumber));
    }

    private int compressNumber(int n) {
        if (n >= mapIn.length) {
            synchronized (this) {
                if (n >= mapIn.length) {
                    var newIn = new int[n * 2];
                    System.arraycopy(mapIn, 0, newIn, 0, mapIn.length);
                    mapIn = newIn;
                }
            }
        }
        if (mapIn[n] == 0) {
            synchronized (this) {
                if (mapIn[n] == 0) {
                    var mapTo = ++numMapped;
                    mapIn[n] = mapTo;
                    if (mapTo >= mapOut.length) {
                        var newOut = new int[mapTo * 2];
                        System.arraycopy(mapOut, 0, newOut, 0, mapOut.length);
                        mapOut = newOut;
                    }
                    mapOut[mapTo] = n;
                }
            }
        }
        //System.out.printf("%d compresses to %d\n", n, mapIn[n]);
        return mapIn[n];
    }

    private int decompressNumber(int x) {
        assert mapOut.length > x && mapOut[x] != 0 : "compressor hasn't mapped " + x;
        //System.out.printf("%d decompresses to %d\n", x, mapOut[x]);
        return mapOut[x];
    }
}