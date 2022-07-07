package com.cero.module.settings;

import com.cero.module.settings.types.SettingType;
import com.google.gson.JsonObject;

public abstract class ModuleSetting {

    public String name;
    public SettingType type;

    public ModuleSetting(String name, SettingType type) {
        this.name = name;
        this.type = type;
    }

    public abstract JsonObject toJSON();
    public abstract void updateFromJSON(JsonObject json);
    public abstract void reset();

}
