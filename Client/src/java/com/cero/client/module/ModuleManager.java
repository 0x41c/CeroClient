package com.cero.client.module;

import com.cero.client.module.category.combat.Velocity;

import java.util.ArrayList;

public class ModuleManager {

    public static ArrayList<Module> modules = new ArrayList<>();

    public static Module getFromName(String name) {
        for (Module module : modules) {
            if (module.name.equals(name)) return module;
        }
        return null;
    }

    public static void populate() {
        // Combat
        new Velocity();
    }
}
