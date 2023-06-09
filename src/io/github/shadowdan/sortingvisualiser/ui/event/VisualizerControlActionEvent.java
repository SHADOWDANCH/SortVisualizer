package io.github.shadowdan.sortingvisualiser.ui.event;

import io.github.shadowdan.sortingvisualiser.algorithm.SortAlgorithm;
import io.github.shadowdan.sortingvisualiser.ui.panel.VisualizerControlPanel;

import java.util.EventObject;
import java.util.Objects;

public class VisualizerControlActionEvent extends EventObject {

    private final VisualizerControlActionEventType type;
    private final SortAlgorithm sortAlgorithm;
    private final long delayMills;
    private final int arraySize;

    public VisualizerControlActionEvent(Object source, VisualizerControlActionEventType type, SortAlgorithm sortAlgorithm,
                                        long delayMills, int arraySize) {
        super(source);
        this.type = type;
        this.sortAlgorithm = sortAlgorithm;
        this.delayMills = delayMills;
        this.arraySize = arraySize;
    }

    public VisualizerControlActionEventType getType() {
        return type;
    }

    public SortAlgorithm getSortAlgorithm() {
        return sortAlgorithm;
    }

    public static VisualizerControlActionEventBuilder builder() {
        return new VisualizerControlActionEventBuilder();
    }

    public long getDelayMills() {
        return delayMills;
    }

    public int getArraySize() {
        return arraySize;
    }

    public enum VisualizerControlActionEventType {
        START,
        PAUSE,
        RESUME,
        STOP,
        UPDATE_DELAY,
        UPDATE_ARRAY_SIZE,
        CHANGE_ALGORITHM
    }

    public static class VisualizerControlActionEventBuilder {
        private VisualizerControlPanel source;
        private VisualizerControlActionEventType type;
        private SortAlgorithm sortAlgorithm;
        private long delayMills;
        private int arraySize;

        private VisualizerControlActionEventBuilder() { }

        public VisualizerControlActionEventBuilder source(VisualizerControlPanel value) {
            this.source = value;
            return this;
        }

        public VisualizerControlActionEventBuilder type(VisualizerControlActionEventType value) {
            this.type = value;
            return this;
        }

        public VisualizerControlActionEventBuilder algorithm(SortAlgorithm value) {
            this.sortAlgorithm = value;
            return this;
        }

        public VisualizerControlActionEventBuilder delay(long value) {
            this.delayMills = value;
            return this;
        }

        public VisualizerControlActionEventBuilder arraySize(int value) {
            this.arraySize = value;
            return this;
        }

        public VisualizerControlActionEvent build() {
            Objects.requireNonNull(source);
            Objects.requireNonNull(type);

            return new VisualizerControlActionEvent(source, type, sortAlgorithm, delayMills, arraySize);
        }
    }
}
