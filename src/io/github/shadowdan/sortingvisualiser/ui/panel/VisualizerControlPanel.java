package io.github.shadowdan.sortingvisualiser.ui.panel;

import io.github.shadowdan.sortingvisualiser.algorithm.*;
import io.github.shadowdan.sortingvisualiser.ui.event.VisualizerControlActionEvent;
import io.github.shadowdan.sortingvisualiser.ui.event.VisualizerControlActionEventListener;
import io.github.shadowdan.sortingvisualiser.util.Constants;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class VisualizerControlPanel extends JPanel {

    protected EventListenerList listenerList = new EventListenerList();

    public VisualizerControlPanel(VisualizerControlActionEventListener initialListener, List<SortAlgorithm> algorithmList) {
        addVisualizerControlActionEventListener(initialListener);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);

        JPanel algorithmPanel = new JPanel(new BorderLayout());
        algorithmPanel.setBackground(Color.BLACK);
        JComboBox<SortAlgorithm> algorithmComboBox = new JComboBox<>();

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setForeground(Color.WHITE);
        algorithmLabel.setLabelFor(algorithmComboBox);
        algorithmPanel.add(algorithmComboBox, BorderLayout.PAGE_END);
        algorithmPanel.add(algorithmLabel, BorderLayout.PAGE_START);
        leftPanel.add(algorithmPanel);

        JPanel arraySizePanel = new JPanel(new BorderLayout());
        arraySizePanel.setBackground(Color.BLACK);
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(299, 2, 1000, 50);
        JSpinner arraySizeField = new JSpinner(spinnerNumberModel);
        JLabel arraySizeFieldLabel = new JLabel("Array size:");
        arraySizeFieldLabel.setForeground(Color.WHITE);
        arraySizeFieldLabel.setLabelFor(arraySizeField);
        arraySizePanel.add(arraySizeFieldLabel, BorderLayout.PAGE_START);
        arraySizePanel.add(arraySizeField, BorderLayout.PAGE_END);
        leftPanel.add(arraySizePanel);
        arraySizeField.addChangeListener(e -> {
            fireVisualizerControlActionEvent(
                    VisualizerControlActionEvent.builder()
                            .source(this)
                            .type(VisualizerControlActionEvent.VisualizerControlActionEventType.UPDATE_ARRAY_SIZE)
                            .arraySize(spinnerNumberModel.getNumber().intValue())
                            .build()
            );
        });
        arraySizeField.setValue(300); // So event be called in startup

        JPanel delayPanel = new JPanel(new BorderLayout());
        delayPanel.setBackground(Color.BLACK);
        SpinnerNumberModel delayNumberModel = new SpinnerNumberModel(5, 0, 1000, 1);
        JSpinner delaySpinner = new JSpinner(delayNumberModel);
        JLabel delaySpinnerLabel = new JLabel("Delay:");
        delaySpinnerLabel.setForeground(Color.WHITE);
        delaySpinnerLabel.setLabelFor(delaySpinner);
        delayPanel.add(delaySpinnerLabel, BorderLayout.PAGE_START);
        delayPanel.add(delaySpinner, BorderLayout.PAGE_END);
        leftPanel.add(delayPanel);
        delaySpinner.addChangeListener(e -> {
            fireVisualizerControlActionEvent(
                    VisualizerControlActionEvent.builder()
                            .source(this)
                            .type(VisualizerControlActionEvent.VisualizerControlActionEventType.UPDATE_DELAY)
                            .delay(delayNumberModel.getNumber().longValue())
                            .build()
            );
        });

        algorithmComboBox.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            fireVisualizerControlActionEvent(
                    VisualizerControlActionEvent.builder()
                            .source(this)
                            .type(VisualizerControlActionEvent.VisualizerControlActionEventType.CHANGE_ALGORITHM)
                            .algorithm((SortAlgorithm) e.getItem())
                            .build()
            );
        });
        for (SortAlgorithm algorithm : algorithmList) {
            algorithmComboBox.addItem(algorithm);
        }

        add(leftPanel, BorderLayout.LINE_START);

        JButton stopButton = new JButton(Constants.STOP_ICON);
        stopButton.setEnabled(false);
        stopButton.setPreferredSize(new Dimension(50, 50));

        JButton startButton = new JButton(Constants.START_ICON);
        startButton.setPreferredSize(new Dimension(50, 50));

        startButton.addActionListener(e -> {
            if (startButton.getText().equals(Constants.PAUSE_ICON)) {
                fireVisualizerControlActionEvent(
                        VisualizerControlActionEvent.builder()
                                .source(this)
                                .type(VisualizerControlActionEvent.VisualizerControlActionEventType.PAUSE)
                                .build()
                );
                startButton.setText(Constants.RESUME_ICON);
                return;
            } else if (startButton.getText().equals(Constants.RESUME_ICON)) {
                fireVisualizerControlActionEvent(
                        VisualizerControlActionEvent.builder()
                                .source(this)
                                .type(VisualizerControlActionEvent.VisualizerControlActionEventType.RESUME)
                                .build()
                );
                startButton.setText(Constants.PAUSE_ICON);
                return;
            }

            stopButton.setEnabled(true);
            arraySizeField.setEnabled(false);
            algorithmComboBox.setEnabled(false);

            fireVisualizerControlActionEvent(
                    VisualizerControlActionEvent.builder()
                            .source(this)
                            .type(VisualizerControlActionEvent.VisualizerControlActionEventType.START)
                            .algorithm((SortAlgorithm) algorithmComboBox.getSelectedItem())
                            .delay(delayNumberModel.getNumber().longValue())
                            .arraySize(spinnerNumberModel.getNumber().intValue())
                            .build()
            );

            startButton.setText(Constants.PAUSE_ICON);
        });

        stopButton.addActionListener(e -> {
            fireVisualizerControlActionEvent(
                    VisualizerControlActionEvent.builder()
                            .source(this)
                            .type(VisualizerControlActionEvent.VisualizerControlActionEventType.STOP)
                            .build()
            );
            arraySizeField.setEnabled(true);
            algorithmComboBox.setEnabled(true);
            stopButton.setEnabled(false);
            startButton.setText(Constants.START_ICON);
        });

        rightPanel.add(stopButton);
        rightPanel.add(startButton);
        add(rightPanel, BorderLayout.LINE_END);
    }

    public void addVisualizerControlActionEventListener(VisualizerControlActionEventListener listener) {
        listenerList.add(VisualizerControlActionEventListener.class, listener);
    }
    public void removeVisualizerControlActionEventListener(VisualizerControlActionEventListener listener) {
        listenerList.remove(VisualizerControlActionEventListener.class, listener);
    }

    void fireVisualizerControlActionEvent(VisualizerControlActionEvent evt) {
        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == VisualizerControlActionEventListener.class) {
                ((VisualizerControlActionEventListener) listeners[i + 1]).visualizerControlActionEventOccurred(evt);
            }
        }
    }
}
