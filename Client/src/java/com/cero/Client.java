package com.cero;


import com.cero.sdk.client.Minecraft;
import com.cero.sdk.client.entity.EntityPlayerSP;
import com.cero.utilities.ClientConstants;
import com.cero.utilities.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {

    static public final Client shared = new Client();;
    String mcName = "";
    ClassLoader mainClassLoader;
    Minecraft minecraft;
    boolean isRunning = true;

    static void entry() { // like a constructor, but easier. Called by loader
        Runnable threadEntry = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("CeroClient thread");
                Client.shared.begin();
            }
        };

        new Thread(threadEntry).start();
    }

    void begin() {
        Logger.info("Beginning java setup.");
        getClassLoader();
        getMinecraft();
        Logger.info("Java setup complete.");
        clientLoop();
    }

    public String getMCName() {

        if (!mcName.equals(""))
            return mcName;

        String mcClassName = "ave"; // We only run on MC 1.8.9 anyways

        if (ClientConstants.isLunar) {
            Logger.info("Using lunar client.");
            String sep = File.separator;
            String jarPath = ClientConstants.lunarHomeDir
                    + sep + "offline" + sep + "1.8.9" + sep + "lunar-prod-optifine.jar";
            String mappingFilePath = sep + "patch" + sep + "v1_8" + sep + "mappings.txt";

            URL jarMappingsURL = null;
            InputStream mappingsStream = null;
            String mappings = null;
            try {
                jarMappingsURL = new URL("jar:file:" + jarPath + "!" + mappingFilePath);
                mappingsStream = jarMappingsURL.openStream();
                mappings = new String(mappingsStream.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException exception) {
                Logger.error("Couldn't get mappings file (" + exception.getMessage() + ")");
            }

            assert mappings != null;
            String[] split = mappings.split(System.lineSeparator());

            for (String line : split) {;
                if (line.contains("ave") && !line.contains("$")) {
                    mcClassName = line.split(" ")[0].replace("\n", "");
                }
            }
        }

        Logger.info("Retrieved minecraft class name: " + mcClassName);

        mcName = mcClassName;
        return mcName;
    }

    void getClassLoader() {
        Logger.info("Getting MC classloader");
        ThreadGroup ourThreadGroup = Thread.currentThread().getThreadGroup();
        Thread[] threadList = new Thread[ourThreadGroup.activeCount()];
        ourThreadGroup.enumerate(threadList);

        Thread mainThread = threadList[0];
        Logger.info("Retrieved main thread: " + mainThread);
        ClassLoader classLoader = mainThread.getContextClassLoader();
        Logger.info("Retrieved classloader: " + classLoader);

        mainClassLoader = classLoader;
    }

    void getMinecraft() {
        Logger.info("Getting minecraft");
        try {
            Class<?> mcClazz = mainClassLoader.loadClass(getMCName());
            Field mcField = mcClazz.getDeclaredFields()[10];
            mcField.setAccessible(true);
            try {
                minecraft = new Minecraft(mcClazz, mcField.get(mcClazz));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.error("Couldn't get mc field: " + e.getMessage());
            }
            assert minecraft != null;
            minecraft.loadAllFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.error("Couldn't get class: " + e.getMessage());
        }
        Logger.info("Retrieved minecraft: " + minecraft.instance);
    }

    void clientLoop() {

        boolean printedEnter = false;

        while (isRunning) {

            minecraft.loadAllFields();

            if (minecraft.inWorld()) {
                if (!printedEnter) {
                    Logger.info("Entered world.");
                    printedEnter = true;
                }
                EntityPlayerSP player = minecraft.thePlayer;
                if (player.maxHurtTime > 0 && player.hurtTime == player.maxHurtTime) {
                    Logger.info("was hit");
                    player.motionX *= 0.4;
                    player.motionZ *= 0.4;
                }

            } else {
                if (printedEnter) {
                    Logger.info("Exited world.");
                    printedEnter = false;
                }
            }

            minecraft.applyChanges();

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Logger.info("Thread interrupted? " + e.toString());
            }
        }
    }

}