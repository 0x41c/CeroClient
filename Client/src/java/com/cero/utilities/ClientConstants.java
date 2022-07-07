package com.cero.utilities;

import java.io.File;
import java.util.Objects;

public class ClientConstants {

    // Base directories
    public static final String homeDirectory = System.getProperty("user.home");
    public static final String clientHomeDir = homeDirectory + File.separator + ".ceroclient";
    public static final String lunarHomeDir = homeDirectory + File.separator + ".lunarclient";

    // Logging
    public static final String logsDir = clientHomeDir + File.separator + "logs";
    public static final String logFileDir = logsDir + File.separator + "current.log";

    // Persistent configurations.
    public static final String configFilePath = clientHomeDir + File.separator + "conf.json";
    public static final boolean isLunar = Objects.equals(System.getProperty("java.vendor"), "Azul Systems, Inc.");
}
