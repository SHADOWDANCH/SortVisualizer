package io.github.shadowdan.sortingvisualiser;

import io.github.shadowdan.sortingvisualiser.algorithm.*;
import io.github.shadowdan.sortingvisualiser.sorting.SortingProperties;
import io.github.shadowdan.sortingvisualiser.ui.panel.VisualizerControlPanel;
import io.github.shadowdan.sortingvisualiser.ui.panel.VisualizerPanel;
import io.github.shadowdan.sortingvisualiser.sorting.SortingWorker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SortingVisualizerMain {

    private static final List<SortAlgorithm> ALGORITHMS = List.of(
            new InsertionSortAlgorithm(),
            new BubbleSortAlgorithm(),
            new SelectionSortAlgorithm(),
            new QuickSortAlgorithm(),
            new MergeSortAlgorithm()
    );

    private static SortingWorker sortingWorker;

    public static void main(String... s) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame("Sorting Visualizer");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            jFrame.setLayout(new BorderLayout());

            List<Integer> unsortedValues = new ArrayList<>();
            VisualizerPanel visualizerPanel = new VisualizerPanel();
            VisualizerControlPanel visualizerControlPanel = new VisualizerControlPanel(evt -> {
                switch (evt.getType()) {
                    case START -> {
                        sortingWorker = new SortingWorker(
                                visualizerPanel,
                                evt.getSortAlgorithm(),
                                new SortingProperties(evt.getDelayMills(), evt.getArraySize())
                        );
                        sortingWorker.execute();
                    }
                    case PAUSE -> sortingWorker.pause();
                    case RESUME -> sortingWorker.resume();
                    case STOP -> {
                        visualizerPanel.setValues(unsortedValues.stream().mapToInt(value -> value).toArray());
                        sortingWorker.cancel(true);
                        sortingWorker = null;
                    }
                    case UPDATE_DELAY -> {
                        if (sortingWorker == null) {
                            return;
                        }
                        sortingWorker.getSortingProperties().setDelayMills(evt.getDelayMills());
                    }
                    case UPDATE_ARRAY_SIZE -> {
                        int newArraySize = evt.getArraySize();
                        if (sortingWorker != null) {
                            sortingWorker.getSortingProperties().setArraySize(newArraySize);
                        }
                        List<Integer> newValues = Arrays.asList(IntStream.rangeClosed(1, newArraySize).boxed().toArray(Integer[]::new));
                        Collections.shuffle(newValues);
                        unsortedValues.clear();
                        unsortedValues.addAll(newValues);
                        visualizerPanel.setValues(unsortedValues.stream().mapToInt(value -> value).toArray());
                        visualizerPanel.repaint();
                    }
                    case CHANGE_ALGORITHM -> {
                        visualizerPanel.setValues(unsortedValues.stream().mapToInt(value -> value).toArray());
                        visualizerPanel.repaint();
                    }
                }
            }, ALGORITHMS);
            jFrame.add(visualizerPanel, BorderLayout.CENTER);
            jFrame.add(visualizerControlPanel, BorderLayout.PAGE_END);
            jFrame.setVisible(true);
        });
    }
}
