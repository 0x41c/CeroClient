package com.cero.client.utilities;

import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.cero.client.utilities.ClientConstants.clientHomeDir;
import static com.cero.client.utilities.ClientConstants.logFileDir;

@NonNls
public enum Logger {
    ;

    private static void logRaw(final @NonNls String message) {
        // These should have been created and verified by the injector and loader.
        final File homeDir = new File(clientHomeDir);
        if (!homeDir.exists()) System.exit(1);
        final File logsDir = new File(ClientConstants.logsDir);
        if (!logsDir.exists()) System.exit(1);

        try {
            final FileWriter logs = new FileWriter(logFileDir, true);
            logs.write(message + "\n"); logs.close();
        } catch (final IOException ignored) { System.exit(1);}
    }

    static void logStatus(final String statusName, final String message) {
        Logger.logRaw("[CeroClient/Java/" + statusName + "] " + message);
    }

    public static void info(final String message) {
        Logger.logStatus("INFO", message);
    }

    public static void warning(final String message) {
        Logger.logStatus("WARN", message);
    }

    public static void error(final String message) {
        Logger.logStatus("ERROR", message);
        System.exit(1);
    }

}