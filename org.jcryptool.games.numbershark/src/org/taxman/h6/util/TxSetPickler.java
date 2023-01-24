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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class TxSetPickler extends TxPickler<TxSet> {

    public TxSetPickler() {
        super();
    }

    public TxSetPickler(int bufferSize) {
        super(bufferSize);
    }

    @Override
    public void toStream(TxSet set, DataOutputStream out) throws IOException {
        out.writeShort(set.size());
        for (int i = set.max(); i > 0;  i = set.nextHighest(i)) {
            out.writeShort(i);
        }
    }

    @Override
    public TxSet fromStream(DataInputStream in) throws IOException {
        return new TxSet(readBitSet(in));
    }

    protected BitSet readBitSet(DataInputStream in) throws IOException {
        var bitCount = in.readShort();
        var bits = new BitSet();
        for (int i = 0; i < bitCount; i++) {
            bits.set(in.readShort(), true);
        }
        return bits;
    }
}
