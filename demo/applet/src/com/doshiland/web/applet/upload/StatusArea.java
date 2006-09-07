package com.doshiland.web.applet.upload;

import java.awt.Color;

import javax.swing.JTextArea;

/**
 * UI status textare to show the progress and log messages.
 * 
 * @author Jitesh Doshi
 */
public class StatusArea extends JTextArea {
    public StatusArea(int rows, int columns) {
        super(rows, columns);
        setBackground(Color.WHITE);
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
