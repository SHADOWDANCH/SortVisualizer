package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;

import java.awt.*;
import java.util.Map;

public class BubbleSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] inputArray, SortingMetrics metrics, SortCallback sortCallback) {
        int i, j;
        boolean swapped;
        for (i = 0; i < inputArray.length - 1; i++) {
            swapped = false;
            for (j = 0; j < inputArray.length - i - 1; j++) {
                if (inputArray[j] > inputArray[j + 1]) {
                    sortCallback.onIteration(Map.of(j, Color.RED, j + 1, Color.RED));
                    int x = inputArray[j];
                    inputArray[j] = inputArray[j + 1];
                    inputArray[j + 1] = x;
                    metrics.incrementArrayAccessCount(2);
                    swapped = true;
                }
                metrics.incrementArrayAccessCount(2);
                metrics.incrementComparisonsCount(1);
            }
            if (!swapped)
                break;
        }
    }

    @Override
    public String getName() {
        return "BubbleSort";
    }

    @Override
    public String toString() {
        return getName();
    }
}