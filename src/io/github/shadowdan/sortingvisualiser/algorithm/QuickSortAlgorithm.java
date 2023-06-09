package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;
import io.github.shadowdan.sortingvisualiser.util.MapBuilder;

import java.awt.*;

public class QuickSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] inputArray, SortingMetrics metrics, SortCallback sortCallback) {
        qSort(inputArray, 0, inputArray.length - 1, metrics, sortCallback);
    }

    @Override
    public String getName() {
        return "QuickSort";
    }

    @Override
    public String toString() {
        return getName();
    }

    private int partition(int[] a, int start, int end, SortingMetrics metrics, SortCallback sortCallback) {
        int pivot = a[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (a[j] < pivot) {
                sortCallback.onIteration(
                        MapBuilder.builder(i, Color.RED)
                                .add(j, Color.RED)
                                .add(pivot, Color.GREEN)
                                .build()
                );
                i++;
                int swapTemp = a[i];
                a[i] = a[j];
                a[j] = swapTemp;
                metrics.incrementArrayAccessCount(2);
            }
            metrics.incrementArrayAccessCount(1);
            metrics.incrementComparisonsCount(1);
        }
        sortCallback.onIteration(
                MapBuilder.builder(i + 1, Color.RED)
                        .add(end, Color.RED)
                        .build()
        );
        int swapTemp = a[i + 1];
        a[i + 1] = a[end];
        a[end] = swapTemp;
        metrics.incrementComparisonsCount(2);
        return i + 1;
    }

    private void qSort(int[] a, int l, int r, SortingMetrics metrics, SortCallback sortCallback) {
        if (l < r) {
            int partitionIndex = partition(a, l, r, metrics, sortCallback);
            qSort(a, l, partitionIndex - 1, metrics, sortCallback);
            qSort(a, partitionIndex + 1, r, metrics, sortCallback);
        }
    }
}