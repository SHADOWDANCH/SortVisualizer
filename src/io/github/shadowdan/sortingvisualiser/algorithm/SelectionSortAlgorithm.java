package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;
import io.github.shadowdan.sortingvisualiser.util.MapBuilder;

import java.awt.*;

public class SelectionSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] inputArray, SortingMetrics metrics, SortCallback sortCallback) {
        for (int i = 0; i < inputArray.length - 1; ++i) {
            for (int j = i + 1; j < inputArray.length; ++j) {
                if (inputArray[j] < inputArray[i]) {
                    sortCallback.onIteration(
                            MapBuilder.builder(i, Color.RED)
                                    .add(j, Color.RED)
                                    .build()
                    );
                    int x = inputArray[i];
                    inputArray[i] = inputArray[j];
                    inputArray[j] = x;
                    metrics.incrementArrayAccessCount(2);
                }
                metrics.incrementComparisonsCount(1);
                metrics.incrementArrayAccessCount(2);
            }
        }
    }

    @Override
    public String getName() {
        return "SelectionSort";
    }

    @Override
    public String toString() {
        return getName();
    }
}