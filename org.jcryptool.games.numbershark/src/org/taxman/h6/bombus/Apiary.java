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
import org.taxman.h6.util.TxList;
import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Apiary {
    final Board board;
    List<Hive> hives;
    private final Map<Integer, Hive> hiveMap;
    final TxSet treatAsSources;    // "special" numbers we're going to treat as sources
    private final TxSet effectiveSources;  // all sources
    private final TxSet effectiveSinks;    // all sinks
    private final TxSet[] factorTable;
    private final TxSet[] compositeTable;


    public Apiary(Board board, Namer namer) {
        this(board, TxUnmodifiableSet.EmptySet, namer);
    }


    public Apiary(Board board, TxSet treatAsSources, Namer namer) {
        this.board = board;
        this.treatAsSources = treatAsSources;
        assert board.set.contains(treatAsSources) : "board does not contain all requested sources: "
                + treatAsSources + " the board is missing " + TxSet.subtract(treatAsSources, board.set);
        this.hiveMap = new HashMap<>();
        this.effectiveSources = TxSet.or(TxSet.of(sources()), treatAsSources);
        this.effectiveSinks =  TxSet.of(sinks());

        var max = board.set.max();
        TxSet empty = TxUnmodifiableSet.EmptySet;
        this.factorTable = new TxSet[max+1];
        this.compositeTable = new TxSet[max+1];
        IntStream.rangeClosed(1, max).forEach(n -> {
            factorTable[n] = effectiveSinks.contains(n) ? empty : TxSet.subtract(board.factors(n), effectiveSources);
            compositeTable[n] = effectiveSources.contains(n) ? empty : TxSet.subtract(board.composites(n), effectiveSinks);
        });

        this.effectiveSources.forEach(this::addSource);
        sweepRemainder();
        hives = Collections.unmodifiableList(orderHives(hiveMap.values()));
        hives.forEach(h -> h.setName(namer.getNameForSources(h.sources())));
        finishSetup();
    }

    public Hive merge(List<Hive> hivesToMerge) {
        assert hivesToMerge.size() > 1;
        Hive result = Hive.merge(hivesToMerge);
        result.allNumbers().stream()
                .forEach(x -> hiveMap.put(x, result));
        this.hives = new ArrayList<>(this.hives);
        this.hives.add(this.hives.indexOf(hivesToMerge.get(0)), result);
        this.hives.removeAll(hivesToMerge);
        return result;
    }

    public List<Hive> hives() {
        return new ArrayList<>(hives);
    }

    // run setup on every hive, then keep running on any neighbors of hives that have changed
    public void finishSetup() {
        hives.forEach(Hive::removeImpossible);

        Set<Hive> todo = new HashSet<>(hives);
        while (todo.size() > 0) {
            Hive[] arr = hives.stream()
                    .filter(todo::contains)
                    .toArray(Hive[]::new);
            todo.clear();
            for (Hive h: arr) {
                if (h.finishSetup()) {
                    todo.addAll(h.downstream());
                    todo.addAll(h.upstream());
                }
            }
        }
    }

    public void finishFrameSetup() {
        finishSetup();
        for (var h: hives) h.finishFrameSetup();
    }

    TxSet allSources() {
        return TxSet.of(
                hives.stream()
                        .map(Hive::sources)
                        .flatMapToInt(TxSet::stream)
        );
    }

    int sumOfSources() {
        return allSources().sum();
    }

    TxSet factors(int n) {
        return factorTable[n];
    }

    TxSet composites(int n) {
        return compositeTable[n];
    }

    private IntStream sources() {
        // using this to bootstrap effectiveSources, so can't use effectiveSources here
        return board.set.filter(n -> treatAsSources.contains(n) || board.composites(n).isEmpty());
    }

    private IntStream sinks() {
        // using this to bootstrap effectiveSinks, so can't use effectiveSinks here
        return board.set.filter(n -> TxSet.subtract(board.factors(n), treatAsSources).isEmpty());
    }

    TxSet movesAvailableUsingThisFactorAsTaxOutsideHive(int factor) {
        return TxSet.of(
          composites(factor).stream()
                .filter(composite -> {
                    Hive fHive = hiveMap.get(factor);
                    Hive cHive = hiveMap.get(composite);
                    assert cHive != null;
                    //System.out.println("can add " + composite + " to this hive: " + fHive.canAddSourceToThisHive(composite));
                    return cHive != fHive && cHive.hasFreeFactors() && fHive.canAddSourceToThisHive(composite);
                })
        );
    }

    private void addSource(int n) {
        Hive newHive = new Hive(this);
        newHive.addSource(n);
        hiveMap.put(n, newHive);

        for (int factor : factors(n).streamDescending().toArray()) {
            Hive otherHive = hiveMap.getOrDefault(factor, null);
            if (otherHive != null) {
                otherHive.subsume(newHive);
                newHive.allNumbers().stream()
                        .forEach(x -> hiveMap.put(x, otherHive));
                newHive = otherHive;
            } else {
                newHive.addFactor(factor);
                hiveMap.put(factor, newHive);
            }
        }
    }

    static List<Hive> orderHives(Collection<Hive> hives) {
        List<Hive> result = new ArrayList<>();
        Set<Hive> done = new HashSet<>();
        Set<Hive> uniqueHives = new HashSet<>(hives);

        Queue<Hive> queue = uniqueHives.stream()
                .sorted(Comparator.comparingInt(h -> h.sources().min()))  // enables consistent naming
                .filter(h -> h.downstream().size() == 0)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            Hive h = queue.remove();
            if (!done.contains(h)) {
                result.add(h);
                done.add(h);
                List<Hive> upstreamSorted = h.upstream().stream()
                        .filter(hh -> done.containsAll(hh.downstream()))  // don't take until all downstream are in
                        .sorted(Comparator.comparingInt(hh -> hh.sources().min()))  // enables consistent naming
                        .collect(Collectors.toList());
                queue.addAll(upstreamSorted);
            }
        }

        assert uniqueHives.size() == result.size() : uniqueHives.size() + " unique hives, " + result.size() + " in result";
        return result;
    }

    private void sweepRemainder() {
        //create a new hive for each in order to avoid creating cycles in the graph
        board.set.stream().forEach(n -> {
            if (!hiveMap.containsKey(n)) {
                Hive remainder = new Hive(this);
                remainder.addFactor(n);
                hiveMap.put(n, remainder);
            }
        });
    }

    Hive getHiveForNumber(int n) {
        return hiveMap.get(n);
    }

    public String debugDump(String prefix) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try {
            PrintStream ps = new PrintStream(baos, true, utf8);
            if (treatAsSources.size() > 0)
                ps.println("treat as sources: " + treatAsSources);
            hives.forEach(x -> x.debugDump(ps));
            return baos.toString(utf8).replaceAll("\n", "\n"+prefix);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public TxList getSolution() {
        return hives.stream()
                .map(Hive::getSolution)
                .collect(TxList::new, TxList::appendAll, TxList::appendAll);
    }

    public TxSet getImpossible() {
        return hives.stream()
                .map(Hive::getImpossible)
                .collect(TxSet.collector());
    }

    public TxSet getPromotionCandidateNumbers() {
        return hives.stream()
                .map(Hive::getPromotionCandidateNumbers)
                .collect(TxSet.collector());
    }
}