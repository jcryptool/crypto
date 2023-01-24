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

import org.taxman.h6.game.Board;
import org.taxman.h6.game.Solution;
import org.taxman.h6.util.TxList;
import org.taxman.h6.util.TxSet;

public class BombusSolution extends Solution {
    public final TxSet promotions;

    public BombusSolution(Board b, TxList m, TxSet promotions) {
        super(b, m);
        this.promotions = promotions;
    }

    public static BombusSolution upgrade(Solution sln) {
        var moves = TxList.of(sln.moves);
        var a = new Apiary(sln.board, new Namer());
        var candidates = a.getPromotionCandidateNumbers();
        var promotions = TxSet.and(candidates, TxSet.of(moves));
        return new BombusSolution(sln.board, moves, promotions);
    }
}
