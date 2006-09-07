package com.doshiland.web.applet.upload;

import javax.swing.JApplet;

public class UploadApplet extends JApplet {
    private static final String PARAM_APPNAME = "appName";

    private static final String PARAM_APPPASSWORD = "appPassword";

    private static final String PARAM_TARGETFOLDER = "targetFolder";

    private UploadPanel uploadPanel;

    public void init() {
        super.init();
        String appName = getParameter(PARAM_APPNAME);
        String appPassword = getParameter(PARAM_APPPASSWORD);
        String targetFolder = getParameter(PARAM_TARGETFOLDER);
        uploadPanel = new UploadPanel(appName, appPassword, targetFolder);
        this.getContentPane().add(uploadPanel);
    }
}
