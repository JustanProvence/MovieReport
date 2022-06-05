package com.jp;


import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class View extends JFrame {

    private Logger logger = Logger.getLogger("View");
    private JProgressBar bar;
    private JLabel status;

    public View() {
        super("Movie Report Progress");

        super.setSize(250, 100);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setupComponent();
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void setProgressMax(int max) {
        bar.setMaximum(max);
    }

    public void setProgress(int progress) {
        bar.setValue(progress);
    }

    private void setupComponent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

        bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(true);

        status = new JLabel("...");

        mainPanel.add(bar, BorderLayout.NORTH);
        mainPanel.add(status, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
