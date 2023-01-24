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

package org.taxman.h6.bombus;

import org.taxman.h6.util.TxSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Namer {
    private final Map<Integer, String> numberToName = new HashMap<>();
    private int nextNumber = 0;

    String getNameForSources(TxSet sources) {
        String result;
        List<String> alreadyAssigned = sources.filter(numberToName::containsKey)
                .mapToObj(numberToName::get)
                .distinct()
                .collect(Collectors.toList());
        if (alreadyAssigned.size() > 0) {
            result = String.join("+", alreadyAssigned);
        } else {
            result = nextName();
            sources.forEach(n -> numberToName.put(n, result));
        }
        return result;
    }

    private String nextName() {
        return intToName(nextNumber++);
    }

    // Generate names like Excel column names A, B, C ... AA, AB, ...
    private static String intToName(int i) {
        final int base = 26;
        final char remainderLetter = (char) (i % base + 'A');
        final int divided = i/base;
        return ((divided > 0) ? intToName(divided - 1) : "") + remainderLetter;
    }
}
