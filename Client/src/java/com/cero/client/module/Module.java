package com.cero.client.module;

import com.cero.client.Client;
import com.cero.client.module.category.ModuleCategory;
import com.cero.client.module.settings.ModuleSetting;
import com.cero.client.sdk.client.Minecraft;
import com.cero.client.utilities.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public abstract class Module implements ModuleInterface {

    protected String name = null;
    protected String description;
    protected ModuleCategory category = null;
    protected boolean isEnabled = false;

    protected List<ModuleSetting> settings;

    protected Minecraft minecraft = Client.shared.minecraft;

    protected int keybindCode = 0;

    public Module(String name, String description, ModuleCategory category, boolean defaultEnabled) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.isEnabled =  defaultEnabled;
    }

    public Module(ModuleManager manager) {
        Logger.error("Initializer for module should be overwritten by " + this.getClass());
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ModuleCategory getCategory() { return category; }
    public boolean getEnabled() { return isEnabled; }
    public List<ModuleSetting> getSettings() { return settings; }

    public void toggle() {
        isEnabled = !isEnabled;
        if (isEnabled) onEnable();
        else onDisable();
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public abstract void onUpdate();

    public void updateFromJSON(JsonObject json) {
        isEnabled = json.get("isEnabled").getAsBoolean();
        JsonArray settingsArray = json.get("settings").getAsJsonArray();
        for (ModuleSetting setting : settings) {
            JsonObject objectToUpdate = null;
            for (JsonElement jsonSetting : settingsArray) {
                JsonObject jsonSettingObj = jsonSetting.getAsJsonObject();
                String name = jsonSettingObj.get("name").getAsString();
                if (name.equals(setting.name)) objectToUpdate = jsonSettingObj;
            }
            if (objectToUpdate == null)
                Logger.error("Couldn't update setting " + setting.name + " in module " + name);
            setting.updateFromJSON(objectToUpdate);
        }
    }

    public JsonObject toJSON() {
        JsonObject newObj = new JsonObject();
        newObj.addProperty("isEnabled", isEnabled);
        newObj.addProperty("name", name);
        JsonArray settingsArray = new JsonArray();
        for (ModuleSetting setting : settings) {
            settingsArray.add(setting.toJSON());
        }
        newObj.add("settings", settingsArray);
        return newObj;
    }

    // Implicit default init
    {
        ModuleManager.modules.add(this);
    }
}
