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

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

// Optimal scores up to 519 taken from https://oeis.org/A019312/b019312.txt

public class OptimalResult {  // would make a nice record, but Moshi doesn't know how to deal with records

    private final static String JSON_DATA_FILE = "/optimal.json";
    private static Map<Integer, OptimalResult> resultMap = null;

    public final int n;
    public final int score;
    public final int[] moves;

    OptimalResult(int n, int score, int[] moves) {
        this.n = n;
        this.score = score;
        this.moves = moves;
        checkMoves();
    }

    private void checkMoves() {
        if (moves != null) {
            int sum = Arrays.stream(moves).sum();
            assert sum == score : "for n=" + n + " sum of moves is " + sum + " but score is " + score;
        }
    }

    OptimalResult(int n, int score) {
        this(n, score, null);
    }

    @Override
    public String toString() {
        String moveString = (moves != null) ? Arrays.toString(moves) : "(none)";
        return String.format("(n=%d, score=%d, moves=%s)", n, score, moveString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptimalResult that = (OptimalResult) o;
        return n == that.n && score == that.score && Arrays.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n, score);
        result = 31 * result + Arrays.hashCode(moves);
        return result;
    }

    public String oeisFormat() {
        return String.format("%d %d", n, score);
    }

    public String movesToString(String sep) {
        String moveString;
        if (moves != null) {
            moveString = Arrays.stream(moves)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(sep));
        } else {
            moveString = "(none)";
        }
        return moveString;
    }

    public String hoeySolutionFormat() {
        return String.format("a(%d)=%d: %s", n, score, movesToString(","));
    }

    private static synchronized void loadMap() {
        if (resultMap == null) {
            var is = OptimalResult.class.getResourceAsStream(JSON_DATA_FILE);
            var r = new BufferedReader(new InputStreamReader(is));
            var json = r.lines().collect(Collectors.joining("\n"));
            try {
                resultMap = toMap(fromJson(json));
            } catch (IOException e) {
                throw new RuntimeException("failed loading json", e);
            }
        }
    }

    public static OptimalResult get(int n) {
        loadMap();
        return resultMap.getOrDefault(n, null);
    }

    public static List<OptimalResult> getAll() {
        loadMap();
        return resultMap.keySet().stream()
                .sorted()
                .map(k -> resultMap.get(k))
                .collect(Collectors.toList());
    }

    private static JsonAdapter<List<OptimalResult>> makeAdapter() {
        Moshi moshi = new Moshi.Builder().build();
        Type resultList = Types.newParameterizedType(List.class, OptimalResult.class);
        return moshi.adapter(resultList);
    }

    static String toJson(List<OptimalResult> results) {
        return makeAdapter().toJson(results);
    }

    static List<OptimalResult> fromJson(String json) throws IOException {
        var result = makeAdapter().fromJson(json);
        assert result != null;
        result.forEach(OptimalResult::checkMoves);
        return result;
    }

    static Map<Integer, OptimalResult> toMap(List<OptimalResult> list) throws IOException {
        var seen = new HashSet<Integer>();
        return list.stream()
                .peek(r -> {
                    if (seen.contains(r.n)) throw new RuntimeException("multiple entries for " + r.n);
                    seen.add(r.n);
                })
                .collect(Collectors.toMap(r -> r.n, r -> r));
    }

}