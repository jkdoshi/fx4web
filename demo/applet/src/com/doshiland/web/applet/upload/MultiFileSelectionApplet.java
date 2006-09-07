package com.doshiland.web.applet.upload;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MultiFileSelectionApplet extends JApplet implements ActionListener {
    private static final String PARAM_CUR_DIR = "currentDirectory";

    private JButton btn;

    private File[] files;

    public void showOpenDialog() {
        JFileChooser chooser;
        String folder = getParameter(PARAM_CUR_DIR);
        if (folder != null) {
            chooser = new JFileChooser(folder);
        } else {
            chooser = new JFileChooser();
        }
        chooser.setMultiSelectionEnabled(true);
        int code = chooser.showOpenDialog(this);
        if (code == JFileChooser.APPROVE_OPTION) {
            files = chooser.getSelectedFiles();
        }
    }

    public String[] getFiles() {
        String[] retval = null;
        if (files != null) {
            retval = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                retval[i] = String.valueOf(files[i]);
            }
        }
        return retval;
    }

    public int getFileCount() {
        if (files == null) {
            return 0;
        } else {
            return files.length;
        }
    }

    public String getFile(int index) {
        return String.valueOf(files[index]);
    }

    public void init() {
        super.init();
        this.getContentPane().add(btn = new JButton("Select Files"));
        btn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            showOpenDialog();
        }
    }

    public void start() {
        super.start();
        showOpenDialog();
    }
}
