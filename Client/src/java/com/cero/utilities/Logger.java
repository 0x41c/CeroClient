package com.cero.utilities;

import java.lang.System;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.cero.utilities.ClientConstants.clientHomeDir;
import static com.cero.utilities.ClientConstants.logFileDir;

public class Logger {

    static void logRaw(String message) {
        // These should have been created and verified by the injector and loader.
        File homeDir = new File(clientHomeDir);
        if (!homeDir.exists()) System.exit(1);
        File logsDir = new File(ClientConstants.logsDir);
        if (!logsDir.exists()) System.exit(1);

        try {
            FileWriter logs = new FileWriter(logFileDir, true);
            logs.write(message + "\n"); logs.close();
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