package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;
import io.github.shadowdan.sortingvisualiser.util.MapBuilder;

import java.awt.*;

public class MergeSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] inputArray, SortingMetrics metrics, SortCallback sortCallback) {
        mergeSort(inputArray, 0, inputArray.length - 1, metrics, sortCallback);
    }

    @Override
    public String getName() {
        return "MergeSort";
    }

    @Override
    public String toString() {
        return getName();
    }

    private void mergeSort(int[] anArrayOfInt, int l, int r, SortingMetrics metrics, SortCallback sortCallback) {
        int[][] B = new int[2][r + 1];
        sortMergingly(anArrayOfInt, B, l, r, metrics, sortCallback);
    }

    private void sortMergingly(int[] anArrayOfInt, int[][] B, int l, int r, SortingMetrics metrics, SortCallback sortCallback) {
        if (l >= r)
            return;
        int last = (l + r) / 2;
        sortMergingly(anArrayOfInt, B, l, last, metrics, sortCallback);
        sortMergingly(anArrayOfInt, B, last + 1, r, metrics, sortCallback);
        merge(anArrayOfInt, B, l, last, r, metrics, sortCallback);
    }

    private void merge(int[] anArrayOfInt, int[][] B, int l, int q, int r, SortingMetrics metrics, SortCallback sortCallback) {
        for (int i = l; i <= q; i++) {
            B[0][i] = i;
            B[1][i] = i;
        }
        for (int i = r; i > q; i--) {
            B[0][i] = r + q + 1 - i;
            B[1][i] = r + q + 1 - i;
        }
        int i = l;
        int j = r;
        for (int k = l; k < r; k++) {
            int s = B[0][i];
            int t = B[0][j];
            if (anArrayOfInt[s] <= anArrayOfInt[t]) {
                i++;
            } else {
                s = t;
                j--;
            }
            metrics.incrementArrayAccessCount(2);
            metrics.incrementComparisonsCount(1);
            sortCallback.onIteration(
                    MapBuilder.builder(s, Color.RED)
                            .add(k, Color.RED)
                            .build()
            );
            int x = anArrayOfInt[s];
            anArrayOfInt[s] = anArrayOfInt[k];
            anArrayOfInt[k] = x;
            metrics.incrementArrayAccessCount(2);
            t = B[1][k];
            B[0][t] = s;
            B[1][s] = t;
        }
    }
}