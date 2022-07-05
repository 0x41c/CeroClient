package com.cero.utilities;

import java.io.File;
import java.util.Objects;

public class ClientConstants {
    public static final String homeDirectory = System.getProperty("user.home");
    public static final String clientHomeDir = homeDirectory + File.separator + ".ceroclient";
    public static final String lunarHomeDir = homeDirectory + File.separator + ".lunarclient";
    public static final boolean isLunar = Objects.equals(System.getProperty("java.vendor"), "Azul Systems, Inc.");
}
