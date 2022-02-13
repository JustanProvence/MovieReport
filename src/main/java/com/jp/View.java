package com.jp;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.io.File;
import java.util.List;

public class View extends JFrame {

    private JTextArea statusText;
    private IStatusListener listener;

    public View() {
        super("Running Report...");

        super.setSize(640, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setupComponent();
    }

    public void setStatus(String status) {
        statusText.setText(String.format("%s%n%s", statusText.getText(), status));
    }

    private void setupComponent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        statusText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(statusText);

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void runReport(File root) {
        Status status = new Status();
        status.addListener(new StatusListener());
        MovieReport report = new MovieReport(status, this);
        List<Movie> movies = report.runReport(root);
        report.createCSV(root, movies);
    }

    private class StatusListener implements IStatusListener {

        @Override
        public void statusUpdated(int movieCount, String newStatus) {
            setStatus(newStatus);
        }
    }
}
