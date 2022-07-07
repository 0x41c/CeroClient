package com.cero.config;

import com.cero.module.Module;
import com.cero.module.ModuleManager;
import com.cero.utilities.ClientConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;



public class ConfigurationManager {

    final File configFile = new File(ClientConstants.configFilePath);

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ConfigurationManager() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                writeConfig(); // Writing default config
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadConfig();
        }
    }

    public void loadConfig() {
        try {
            String fileData = IOUtils.toString(new FileInputStream(configFile), StandardCharsets.UTF_8);
            if (!Objects.equals(fileData, "")) {
                Gson gson = new Gson();
                JsonObject configObject = gson.fromJson(fileData, JsonObject.class);
                JsonArray moduleArray = configObject.getAsJsonArray("modules");
                for (JsonElement element : moduleArray) {
                    JsonObject obj = element.getAsJsonObject();
                    String name = obj.get("name").getAsString();
                    Module module = ModuleManager.getFromName(name);
                    if (module != null) {
                        module.updateFromJSON(obj);
                    }
                }
            } else {
                writeConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeConfig() {
        try {
            Writer configWriter = new FileWriter(configFile);
            JsonObject configObject = new JsonObject();
            JsonArray modulesArray = new JsonArray();
            for (Module module : ModuleManager.modules) {
                modulesArray.add(module.toJSON());
            }
            configObject.add("modules", modulesArray);
            Gson gson = new Gson();
            String json = gson.toJson(configObject);
            FileWriter jsonWriter = new FileWriter(configFile);
            jsonWriter.write(json);
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}