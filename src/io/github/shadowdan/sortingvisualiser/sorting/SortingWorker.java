package io.github.shadowdan.sortingvisualiser.sorting;

import io.github.shadowdan.sortingvisualiser.algorithm.SortAlgorithm;
import io.github.shadowdan.sortingvisualiser.ui.panel.VisualizerPanel;

import javax.swing.*;
import java.util.Collections;

public class SortingWorker extends SwingWorker<Void, Void> {

    private final VisualizerPanel visualizerPanel;
    private final SortAlgorithm sorter;
    private final SortingProperties sortingProperties;
    private final SortingMetrics sortingMetrics = new SortingMetrics();

    private final Object pauseLock = new Object();

    public SortingWorker(VisualizerPanel visualizerPanel, SortAlgorithm sorter, SortingProperties sortingProperties) {
        this.visualizerPanel = visualizerPanel;
        this.sorter = sorter;
        this.sortingProperties = sortingProperties;

        visualizerPanel.setSortingMetrics(sortingMetrics);
    }

    @Override
    protected Void doInBackground() {
        visualizerPanel.setSortingState(SortingState.SORTING);
        sorter.sort(visualizerPanel.getValues(), sortingMetrics, (highlightMap) -> {
            try {
                if (visualizerPanel.getSortingState() == SortingState.WAITING) {
                    synchronized (pauseLock) {
                        pauseLock.wait();
                    }
                }
                if (sortingProperties.getDelayMills() > 0) {
                    Thread.sleep(sortingProperties.getDelayMills());
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            visualizerPanel.setHighlights(highlightMap);
            SwingUtilities.invokeLater(visualizerPanel::repaint);
        });
        return null;
    }

    @Override
    protected void done() {
        visualizerPanel.setHighlights(Collections.emptyMap());
        visualizerPanel.setSortingState(isCancelled() ? SortingState.WAITING : SortingState.COMPLETE);
        visualizerPanel.repaint();
    }

    public void pause() {
        visualizerPanel.setSortingState(SortingState.WAITING);
    }

    public void resume() {
        synchronized (pauseLock) {
            visualizerPanel.setSortingState(SortingState.SORTING);
            pauseLock.notifyAll();
        }
    }

    public SortingProperties getSortingProperties() {
        return sortingProperties;
    }
}
