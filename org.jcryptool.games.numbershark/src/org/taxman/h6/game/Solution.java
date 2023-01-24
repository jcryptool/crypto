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

import java.io.PrintStream;
import java.util.stream.Collectors;

public class Solution {
    public final Board board;
    public final TxList moves;


    public Solution(Board b, TxList m) {
        board = b;
        moves = m.unmodifiable();
    }

    public int score() {
        return moves.sum();
    }

    private String makeTaxString(TxSet tax) {
        return tax.stream()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
    }

    public void display(PrintStream ps) throws VerificationException {
        int allPoints = board.set.sum();
        int playerScore = score();
        int taxScore = allPoints - playerScore;
        float percentOfPot = (float) 100.0 * playerScore/allPoints;
        int lowest = moves.min();
        float lowestPercent = (float) 100.0 * lowest / board.set.max();
        ps.printf("player score: %,d\n", playerScore);
        ps.printf("     tax man: %,d\n", taxScore);
        ps.printf("win by %,d with %.1f%% of the pot\n", playerScore - taxScore, percentOfPot);
        ps.printf("lowest number taken: %,d (%.2f%%)\n", lowest, lowestPercent);
        //ps.println("moves: " + moves);
        ps.println("  game:");
        Board remainder = board;
        for(int m: moves.toArray()) {
            Move move = remainder.makeMove(m);
            ps.println(String.format("      take %2d", m) + ", tax man takes " + makeTaxString(move.tax));
            remainder = move.remainder;
        }
        if (remainder.size() > 0) {
            ps.println("      tax man takes remainder: " + makeTaxString(remainder.set));
        } else {
            ps.println("      no remainder");
        }
    }

    public void verify(int boardSize) throws VerificationException {
        Board remainder = board;

        try {
            for (int m : moves.toArray()) {
                Move move = remainder.makeMove(m);
                remainder = move.remainder;
            }

            var opt = OptimalResult.get(boardSize);
            if (opt != null) {
                int thisScore = moves.sum();
                if (opt.score != thisScore) {
                    if (opt.moves != null) {
                        TxSet thisMoves = TxSet.of(moves);
                        TxSet optimalMoves = TxSet.of(opt.moves);
                        var missing = TxSet.subtract(optimalMoves, thisMoves);
                        var extra = TxSet.subtract(thisMoves, optimalMoves);
                        System.out.println();
                        System.out.println("in optimal moves but not in this solution: " + missing);
                        System.out.println("in this solution but not in optimal moves: " + extra);
                    }
                    throw new VerificationException(
                            String.format("for %d the sum of moves should be %,d but came out %,d",
                                    boardSize, opt.score, this.score())
                    );
                }
            }
        } catch (VerificationException ve) {
            throw ve.setSolution(this);
        }
    }
}