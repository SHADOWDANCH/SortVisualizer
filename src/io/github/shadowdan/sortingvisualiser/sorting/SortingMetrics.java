package io.github.shadowdan.sortingvisualiser.sorting;

public class SortingMetrics {

    private int arrayAccessCount = 0;
    private int comparisonsCount = 0;

    public int getArrayAccessCount() {
        return arrayAccessCount;
    }

    public void setArrayAccessCount(int arrayAccessCount) {
        this.arrayAccessCount = arrayAccessCount;
    }

    public int getComparisonsCount() {
        return comparisonsCount;
    }

    public void setComparisonsCount(int comparisonsCount) {
        this.comparisonsCount = comparisonsCount;
    }

    public void incrementArrayAccessCount(int value) {
        arrayAccessCount += value;
    }

    public void incrementComparisonsCount(int value) {
        comparisonsCount += value;
    }
}
