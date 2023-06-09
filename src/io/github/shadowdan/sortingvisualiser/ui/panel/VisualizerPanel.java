package io.github.shadowdan.sortingvisualiser.ui.panel;

import io.github.shadowdan.sortingvisualiser.sorting.SortingMetrics;
import io.github.shadowdan.sortingvisualiser.sorting.SortingState;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VisualizerPanel extends JPanel {

    private Map<Integer, Color> highlightMap = new HashMap<>();
    private int maxValue;
    private SortingState sortingState = SortingState.WAITING;
    private SortingMetrics sortingMetrics = new SortingMetrics();
    private int[] values;

    public VisualizerPanel() {
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        renderMetrics(g2d);
        Color fill = switch (sortingState) {
            case COMPLETE -> Color.GREEN;
            default -> Color.WHITE;
        };
        if (values != null) {
            int width = getWidth() - 1;
            int height = getHeight() - 1;
            colorBars(g2d, values, height, Math.round((float) width / (float) values.length), 0, fill);
        }
        g2d.dispose();
    }

    private void colorBars(Graphics2D g2d, int[] values, int height, int colWidth, int x, Color fill) {
        for (int index = 0; index < values.length; index++) {
            int value = values[index];
            int colHeight = (int) ((float) height * ((float) value / (float) maxValue));
            g2d.setColor(highlightMap.getOrDefault(index, fill));
            g2d.fillRect(x, height - colHeight, colWidth - 1, colHeight);
            x += colWidth;
        }
    }

    private void renderMetrics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
        g2d.drawString("Comparisons = " + sortingMetrics.getComparisonsCount(), 0, 25);
        g2d.drawString("Array accesses = " + sortingMetrics.getArrayAccessCount(), 250, 25);
    }

    public void setHighlights(Map<Integer, Color> highlightMap) {
        this.highlightMap = highlightMap;
    }

    public void setValues(int[] values) {
        this.values = values;
        this.maxValue = Arrays.stream(values).max().orElseThrow();
    }

    public int[] getValues() {
        return values;
    }

    public void setSortingState(SortingState sortingState) {
        this.sortingState = sortingState;
    }

    public SortingState getSortingState() {
        return sortingState;
    }

    public void setSortingMetrics(SortingMetrics sortingMetrics) {
        this.sortingMetrics = sortingMetrics;
    }
}
