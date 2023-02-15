package com.cero.client.module.category;

public enum ModuleCategory {

    COMBAT      (true),
    MOVEMENT    (false),
    UTILITY     (false),
    WORLD       (false),
    RENDER      (false),
    BLATANT     (false),
    LEGIT       (false),
    SERVER      (false);

    String serverName;
    boolean displayModule;
    ModuleCategory(boolean display) { this.displayModule = display;}

    ModuleCategory(String serverName) { this.serverName = serverName; }
}
