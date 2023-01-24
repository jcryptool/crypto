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


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchQueue {
    private static final int MAX_LIST_LEN = 10000;   // allow a thread list to grow to this size before sending to disk

    private final String name;
    private final DiskHelper diskHelper;
    private List<List<TaskData>> taskData;
    private boolean hasBeenShutDown = false;
    private final ThreadLocal<List<TaskData>> taskDataForThread;


    public SearchQueue(int name, SearchQueueManager sqm) {
        this.name = "search queue " + name;
        this.diskHelper = new DiskHelper(sqm.game, name);
        this.taskData = new ArrayList<>();
        this.taskDataForThread = ThreadLocal.withInitial(this::newTaskDataList);
    }

    public String getName() {
        return name;
    }

    private List<TaskData> newTaskDataList() {
        ArrayList<TaskData> list = new ArrayList<>();
        synchronized (this) {
            taskData.add(list);
        }
        return list;
    }

    public void add(TaskData td) {
        assert !hasBeenShutDown;
        var list = taskDataForThread.get();
        list.add(td);

        if (list.size() >= MAX_LIST_LEN) {
            synchronized (this) {
                taskDataForThread.set(newTaskDataList());
                // Can't call taskData.remove() here because it compares elements using the equals() method.
                // Because these lists are being modified by other threads, calling equals() can land us with
                // a ConcurrentModificationException.  So we filter instead in order to remove by comparing pointers.
                taskData = taskData.stream().filter(lst -> lst != list).collect(Collectors.toList());
            }
            diskHelper.sendToDisk(list);
        }
    }

    public void onDeck() {
        if (!hasBeenShutDown) diskHelper.switchToLoading();
    }

    public Stream<TaskData> stream() {
        return Stream.concat(streamList(), diskHelper.stream())
                .onClose(this::shutdown);
    }

    private Stream<TaskData> streamList() {
        return taskData.stream().flatMap(List::stream);
    }

    public void shutdown() {
        // System.out.printf("    shutting down %s\n", diskHelper.getName());
        synchronized (this) {
            taskData.clear(); // The threadlocal still has a pointer to each list, so this won't GC all the lists
        }
        diskHelper.shutdown();
        hasBeenShutDown = true;
    }

    public long getCountOfTasksInMemory() {
        long local = taskData.stream()
                .mapToLong(List::size)
                .sum();
        return local + diskHelper.getCountOfTasksInMemory();
    }

    public long getCountOfTasksOnDisk() {
        return diskHelper.getTaskCountOnDisk();
    }

    public long getTotalTaskCount() {
        return getCountOfTasksInMemory() + getCountOfTasksOnDisk();
    }
}
