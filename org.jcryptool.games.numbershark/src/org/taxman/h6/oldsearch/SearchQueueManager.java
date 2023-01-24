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

package org.taxman.h6.oldsearch;

import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SearchQueueManager {

    private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("HH:mm a");

    private final int min;
    public final int game;
    private final ArrayList<SearchQueue> searchQueues;


    public SearchQueueManager(int max, int min, int game) {
        this.min = min;
        this.game = game;
        this.searchQueues = IntStream.rangeClosed(min, max)
                .mapToObj(i-> new SearchQueue(i, this))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    Stream<TaskData> stream(int target) {
        assert target >= min : String.format("target %d is less than the minimum of %d", target, min);
        if (target - 1 - min >= 0) searchQueues.get(target-1-min).onDeck();
        return searchQueues.get(target-min).stream();
    }

    void add(int target, TaskData td) {
        searchQueues.get(target-min).add(td);
    }

    public void shutdown(int target) {
        //System.out.printf("shutting down search queue %d\n", target);
        searchQueues.get(target-min).shutdown();
    }

    public void shutdownAllBelowThreshold(int threshold) {
        for (int i = min; i < threshold; i++) {
            searchQueues.get(i-min).shutdown();
        }
    }


    public void shutdownAll() {
        searchQueues.stream()
                .parallel()
                .forEach(SearchQueue::shutdown);
    }

    public void statusReport(PrintStream ps) {
        var timestamp = ZonedDateTime.now().format(timestampFormat);
        long total = getTotalTaskCount();
        long onDisk = getCountOfTasksOnDisk();
        double onDiskPercent = (total != 0) ? 100.0 * onDisk / total : 0;

        ps.printf("    %s active targets with %,d tasks (%.1f%% on disk) at %s\n",
                getCountOfActiveSearchTargets(), total, onDiskPercent, timestamp
        );
    }

    private long getCountOfActiveSearchTargets() {
        return searchQueues.stream()
                .mapToLong(SearchQueue::getTotalTaskCount)
                .filter(i -> i > 0)
                .count();
    }

    private long getCountOfTasksInMemory() {
        return searchQueues.stream()
                .mapToLong(SearchQueue::getCountOfTasksInMemory)
                .sum();
    }

    private long getCountOfTasksOnDisk() {
        return searchQueues.stream()
                .mapToLong(SearchQueue::getCountOfTasksOnDisk)
                .sum();
    }

    private long getTotalTaskCount() {
        return getCountOfTasksInMemory() + getCountOfTasksOnDisk();
    }
}
