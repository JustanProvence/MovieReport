package com.jp;

import javax.swing.*;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        if(args.length != 0) {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        File root = new File(args[0]);
                        View view = new View();
                        view.setVisible(true);
                        view.runReport(root);
                    }
                });

            }catch (Throwable t) {
                JOptionPane.showMessageDialog(new JFrame(), t.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "No Inputs Given");
        }

    }
}
