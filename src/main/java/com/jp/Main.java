package com.jp;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {

    private static final Logger logger = Logger.getLogger("Main");
    private static final String LOG_FILE_NAME = "output.txt";
    private static final View view = new View();

    public static void main(String[] args) {
        if(args.length != 0) {
            try {
                File root = new File(args[0]);
                setupLogger(root);
                showView(root);
                runReport(root);
            }catch (Throwable t) {
                JOptionPane.showMessageDialog(new JFrame(), t.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "No Inputs Given");
        }
    }

    private static void setupLogger(File root) {
        FileHandler fh;
        String logFile = String.format("%s%s%s", root.getParent(), File.separator, LOG_FILE_NAME);
        try {
            fh = new FileHandler(logFile, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showView(File root) {
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
            logger.info("Running report on "+root.getAbsolutePath());
        });
    }

    private static void updateMax(final int max) {
        SwingUtilities.invokeLater(() -> {
            view.setProgressMax(max);
        });
    }

    private static void updateProgress(final int progress) {
        SwingUtilities.invokeLater(() -> {
            view.setProgress(progress);
        });
    }

    private static void updateStatus(final String status) {
        SwingUtilities.invokeLater(() -> {
            view.setStatus(status);
        });
    }

    private static void runReport(File root) {
        Status status = new Status();
        StatusListener listener = new StatusListener();
        status.addListener(listener);

        MovieReport reporter = new MovieReport(status);
        List<Movie> movies = reporter.runReport(root);
        reporter.createCSV(root, movies);

        updateStatus(String.format("Finished writing %s movies.", listener.getMax()));
    }

    private static class StatusListener implements IStatusListener {
        private int max = 0;

        @Override
        public void maxUpdated(int totalNumberMovies) {
            max = totalNumberMovies;
            updateStatus(String.format("Found %s movies.", totalNumberMovies));
            updateMax(totalNumberMovies);
        }

        @Override
        public void progressUpdated(int progress) {
            updateProgress(progress);
        }

        public int getMax() {
            return max;
        }
    }
}
