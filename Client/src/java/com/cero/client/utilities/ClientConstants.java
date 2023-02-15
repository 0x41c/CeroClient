package com.cero.client.utilities;

import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.util.Objects;

public class ClientConstants {

    // Base directories
    private static final String homeDirectory = System.getProperty("user.home");
    static final @NonNls String clientHomeDir = ClientConstants.homeDirectory + File.separator + ".ceroclient";
    public static final @NonNls String lunarHomeDir = ClientConstants.homeDirectory + File.separator + ".lunarclient";

    // Logging
    static final String logsDir = ClientConstants.clientHomeDir + File.separator + "logs";
    static final @NonNls String logFileDir = ClientConstants.logsDir + File.separator + "current.log";

    // Persistent configurations.
    public static final String configFilePath = ClientConstants.clientHomeDir + File.separator + "conf.json";
    public static final boolean isLunar = Objects.equals(System.getProperty("java.vendor"), "Azul Systems, Inc.");
}
