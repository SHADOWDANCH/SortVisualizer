package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;
import io.github.shadowdan.sortingvisualiser.util.MapBuilder;

import java.awt.*;

public class InsertionSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] inputArray, SortingMetrics metrics, SortCallback sortCallback) {
        for (int i = 0; i < inputArray.length; ++i) {
            for (int j = i - 1; j >= 0 && inputArray[j] > inputArray[j + 1]; --j) {
                sortCallback.onIteration(
                        MapBuilder.builder(j, Color.RED)
                                .add(j - 1, Color.RED)
                                .add(i, Color.GREEN).build()
                );
                int x = inputArray[j];
                inputArray[j] = inputArray[j + 1];
                inputArray[j + 1] = x;
                metrics.incrementComparisonsCount(1);
                metrics.incrementArrayAccessCount(4);
            }
        }
    }

    @Override
    public String getName() {
        return "InsertionSort";
    }

    @Override
    public String toString() {
        return getName();
    }
}