package com.doshiland.web.applet.upload;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * UI Panel to upload files to IFS.
 * 
 * @author Jitesh Doshi
 */
public class UploadPanel extends JPanel implements ActionListener {
    private JButton btnUpload;

    private JTextArea status;

    private JTextField txtAppName;

    private JTextField txtAppPassword;

    private JTextField txtTargetFolder;

    public UploadPanel(String appName, String appPassword, String targetFolder) {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Application Name"));
        panel.add(txtAppName = new JTextField(appName));
        panel.add(new JLabel("Application Password"));
        panel.add(txtAppPassword = new JTextField(appPassword));
        panel.add(new JLabel("Target Folder"));
        panel.add(txtTargetFolder = new JTextField(targetFolder));
        panel.add(new JLabel("Files to upload"));
        panel.add(btnUpload = new JButton("Select files and upload"));
        this.add(status = new StatusArea(5, 30));
        btnUpload.addActionListener(this);
        log("Welcome!");
    }

    private void upload() {
        File[] files = getFiles();
        if (files != null && files.length > 0) {
                // Uploader uploader = new Uploader(txtAppName.getText(),
                    // txtAppPassword.getText(), txtTargetFolder.getText());
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    log("Uploading " + i + " of " + files.length + ": " + file);
                    this.update(this.getGraphics());
                    // uploader.store(file);
                }
        }
    }

    public void log(Object o) {
        status.append(String.valueOf(o));
        status.append("\n");
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnUpload) {
            upload();
        }
    }

    private File[] getFiles() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(true);
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            return chooser.getSelectedFiles();
        } else {
            return null;
        }
    }
}
