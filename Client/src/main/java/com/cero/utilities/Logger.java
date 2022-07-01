package com.cero.utilities;

import java.lang.System;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    static final String homeDirectory = System.getProperty("user.home") + File.separator + ".ceroclient";

    static void logRaw(String message) {
        File homeDir = new File(homeDirectory);
        if (!homeDir.exists()) System.exit(1);

        String logsDirectory = homeDirectory + File.separator + "logs";
        File logsDir = new File(logsDirectory);

        if (!logsDir.exists()) System.exit(1);

        String logsFile = logsDirectory + File.separator + "current.log";

        try {
            FileWriter logs = new FileWriter(logsFile, true);
            logs.write(message + "\n");
            logs.close();
        } catch (IOException ignored) { System.exit(1);}
    }

    static void logStatus(String statusName, String message) {
        logRaw("[CeroClient/Java/" + statusName + "] " + message);
    }

    public static void info(String message) {
        logStatus("INFO", message);
    }

    public static void warning(String message) {
        logStatus("WARN", message);
    }

    public static void error(String message) {
        logStatus("ERROR", message);
        System.exit(1);
    }

}