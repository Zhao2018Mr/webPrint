package com.cmbird.utils;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.prefs.Preferences;


public class LoginConfig {

    public static final String ProgramName = "打印插件";
    public static final Preferences pref = Preferences.userRoot().node("打印插件");
    public static File path;
    static {
        try {
            path = new File(System.getProperty("user.home") + File.separator + "Desktop");
        } catch (Exception e) {

        }
        if (path == null || !path.exists()) {
            try {
                path = new File(System.getProperty("user.home") + File.separator + "桌面");
            } catch (Exception e) {

            }
        }
        if (path == null || !path.exists()) {
            path = new File(System.getProperty("user.home"));
        }
    }
}
