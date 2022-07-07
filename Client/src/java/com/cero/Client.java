package com.cero;

import com.cero.config.ConfigurationManager;
import com.cero.module.Module;
import com.cero.module.ModuleManager;
import com.cero.sdk.client.Minecraft;
import com.cero.utilities.ClientConstants;
import com.cero.utilities.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    static public final Client shared = new Client();
    String mcName = "";
    ClassLoader mainClassLoader;
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    ConfigurationManager configurationManager;
    public Minecraft minecraft;
    public boolean isRunning = true;

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
        ModuleManager.populate();

        configurationManager = new ConfigurationManager();

        AtomicInteger millisecondPartCounter = new AtomicInteger();

        executor.scheduleAtFixedRate(() -> {

            if (!isRunning) {
                executor.shutdown();
                return;
            }

            minecraft.loadAllFields();

            if (minecraft.inWorld()) {
                for (Module module : ModuleManager.modules) {
                    if (module.getEnabled()) {
                        module.onUpdate();
                    }
                }
            }

            minecraft.applyChanges();

            if (millisecondPartCounter.getAndIncrement() > 40) { // One second
                configurationManager.loadConfig();
                millisecondPartCounter.set(0);
            }

        }, 0 , 25, TimeUnit.MILLISECONDS);
    }

}