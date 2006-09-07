package com.doshiland.web.applet.upload;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Standalone UI Frame to upload files to IFS.
 * 
 * @author Jitesh Doshi
 */
public class UploadFrame extends JFrame {
    private UploadPanel uploadPanel;

    public UploadFrame() {
        super("File Upload");
        Container c = this.getContentPane();
        uploadPanel = new UploadPanel("grs", "grsdev", "/ereg/jdoshi");
        c.add(uploadPanel);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UploadFrame();
    }
}
