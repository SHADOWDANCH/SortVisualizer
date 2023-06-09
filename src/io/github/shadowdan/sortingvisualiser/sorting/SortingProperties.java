package io.github.shadowdan.sortingvisualiser.sorting;

public class SortingProperties {

    private long delayMills = 0;
    private int arraySize = 0;

    public SortingProperties(long delayMills, int arraySize) {
        this.delayMills = delayMills;
        this.arraySize = arraySize;
    }

    public SortingProperties() {
    }

    public long getDelayMills() {
        return delayMills;
    }

    public void setDelayMills(long delayMills) {
        this.delayMills = delayMills;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
}
