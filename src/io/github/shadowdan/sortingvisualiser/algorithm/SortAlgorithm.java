package io.github.shadowdan.sortingvisualiser.algorithm;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;

import java.awt.*;
import java.util.Map;

/**
 * Represents abstract sorting algorithm with support for visualisation.
 * Implementations should be stateless.
 */
public interface SortAlgorithm {

    /**
     * Sorts supplied array.
     * Should fill metrics.
     * Callback should be invoked on every iteration before updating metrics
     *
     * @param inputArray   array that should be sorted
     * @param metrics      metrics
     * @param sortCallback callback for every iteration
     */
    void sort(int[] inputArray,  SortingMetrics metrics, SortCallback sortCallback);

    /**
     * Returns name of the algorithm
     *
     * @return name of the algorithm
     */
    String getName();

    @FunctionalInterface
    interface SortCallback {
        /**
         * Invokes on every sort iteration.
         * Should supply highlightMap.
         *
         * @param highlightMap map of what elements should be highlighted on this iteration
         */
        void onIteration(Map<Integer, Color> highlightMap);
    }
}