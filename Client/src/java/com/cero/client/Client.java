package com.cero.client;

import com.cero.client.config.ConfigurationManager;
import com.cero.client.module.Module;
import com.cero.client.module.ModuleManager;
import com.cero.client.sdk.client.Minecraft;
import com.cero.client.utilities.ClientConstants;
import com.cero.client.utilities.Logger;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    /**
     * Shared instance of client
     */
    public static final Client shared = new Client();
    private String mcName = "";
    private ClassLoader mainClassLoader = null;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ConfigurationManager configurationManager = null;

    /**
     * Main minecraft instance proxy
     */
    public Minecraft minecraft = null;
    /**
     *
     */
    private boolean isRunning = true;

    static void entry() { // like a constructor, but easier. Called by loader
        final Runnable threadEntry = () -> {
            Thread.currentThread().setName("CeroClient thread");
            Client.shared.begin();
        };

        final Thread thread = new Thread(threadEntry);
        thread.start();
    }

    private void begin() {
        Logger.info("Deprecated...");
    }

    private String loadMCName() {

        if (!"".equals(mcName))
            return getMCName();

        String mcClassName = "ave"; // We only run on MC 1.8.9 anyway

        if (ClientConstants.isLunar) {
            // Deprecation
        }

        Logger.info("Retrieved minecraft class name: " + mcClassName);

        mcName = mcClassName;
        return mcName;
    }

    private void dumpLoadedClasses() {

    }

    private void getClassLoader() {
        Logger.info("Getting MC classloader");
        final ThreadGroup ourThreadGroup = Thread.currentThread().getThreadGroup();
        final Thread[] threadList = new Thread[ourThreadGroup.activeCount()];
        ourThreadGroup.enumerate(threadList);

        final Thread mainThread = threadList[0];
        Logger.info("Retrieved main thread: " + mainThread);
        final ClassLoader classLoader = mainThread.getContextClassLoader();
        Logger.info("Retrieved classloader: " + classLoader);

        mainClassLoader = classLoader;
    }

    private void getMinecraft() {
        Logger.info("Getting minecraft");
        try {
            final String loadedMCName = loadMCName();
            final Class<?> mcClazz = mainClassLoader.loadClass(loadedMCName);
            Field mcField = mcClazz.getDeclaredFields()[10];
            mcField.setAccessible(true);
            try {
                final Object minecraftInstance = mcField.get(mcClazz);
                minecraft = new Minecraft(mcClazz, minecraftInstance);
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
                final String errorMessage = e.getMessage();
                Logger.error("Couldn't get mc field: " + errorMessage);
            }

            assert null != minecraft;
            minecraft.loadAllFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.error("Couldn't get class: " + e.getMessage());
        }
        Logger.info("Retrieved minecraft: " + minecraft.instance);
    }

    private void clientLoop() {
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
                for (final Module module : ModuleManager.modules) {
                    if (module.getEnabled()) {
                        module.onUpdate();
                    }
                }
            }

            minecraft.applyChanges();

            if (40 < millisecondPartCounter.getAndIncrement()) {
                this.configurationManager.loadConfig();
                millisecondPartCounter.set(0);
            }

        }, 0L, 25L, TimeUnit.MILLISECONDS);
    }

    private String getMCName() {
        return mcName;
    }

    private void setMCName(final String mcName) {
        this.mcName = mcName;
    }

    private ClassLoader getMainClassLoader() {
        return mainClassLoader;
    }

    private void setMainClassLoader(final ClassLoader mainClassLoader) {
        this.mainClassLoader = mainClassLoader;
    }

    private ScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(final ScheduledExecutorService executor) {
        this.executor = executor;
    }

    private ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    private void setConfigurationManager(final ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }
}