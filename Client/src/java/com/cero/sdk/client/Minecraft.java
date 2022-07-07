package com.cero.sdk.client;

import com.cero.sdk.client.entity.EntityPlayerSP;
import com.cero.sdk.util.Timer;
import com.cero.sdk.world.World;
import com.cero.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;

public class Minecraft extends Interface {

    public Minecraft(@NotNull Class<?> type, @NotNull Object minecraft) {
        super(type, minecraft);
    }

    public Object logger;
    public Object mojangPngLocation;
    public boolean isRunningOnMac;
    public byte[] memoryReserve;
    public List<Object> macDisplayModes;
    public File fileResourcePacks;
    public Object twitchDetails;
    public Object profileProperties;
    public Object currentServerData;
    public Object renderEngine;
    public Object theMinecraft; // Don't set this to Interface
    public Object playerController;
    public boolean isFullscreen;
    public boolean enableGLErrorTracking;
    public boolean hasCrashed;
    public Object crashReporter;
    public int displayWidth;
    public int displayHeight;
    public boolean connectedToRealms;
    public Timer gameTimer;
    public Object usageSnooper;
    public World theWorld;
    public Object renderGlobal;
    public Object renderManager;
    public Object renderItem;
    public Object itemRenderer;
    public EntityPlayerSP thePlayer;
    public Object renderViewEntity;
    public Object pointedEntity;
    public Object effectRenderer;
    public Object session;
    public boolean isGamePaused;
    public Object fontRenderer;
    public Object standardGalacticFontRenderer;
    public Object currentScreen;
    public Object loadingScreen;
    public Object entityRenderer;
    public int leftClickCounter;
    public int tempDisplayWidth;
    public int tempDisplayHeight;
    public Object theIntegratedServer;
    public Object guiAchievement;
    public Object inGameGUI;
    public boolean skipRenderWorld;
    public Object objectMouseOver;
    public Object gameSettings;
    public Object mouseHelper;
    public File mcDataDir;
    public File fileAssets;
    public String launchedVersion;
    public Object proxy;
    public Object saveLoader;
    public int debugFPS;
    public int rightClickDelayTimer;
    public String serverName;
    public int serverPort;
    public boolean inGameFocus;
    public long systemTime;
    public int joinPlayerCounter;
    public Object frameTimer;
    public long startNanoTime;
    public boolean jvm64Bit;
    public boolean isDemo;
    public Object myNetworkManager;
    public boolean integratedServerIsRunning;
    public Object mcProfiler;
    public long debugCrashKeyPressTime;
    public Object mcResourceManager;
    public Object metadataSerializer;
    public List<Object> defaultResourcePacks;
    public Object mcDefaultResourcePack;
    public Object mcResourcePackRepository;
    public Object mcLanguageManager;
    public Object stream;
    public Object frameBufferMC;
    public Object textureMapBlocks;
    public Object mcSoundHandler;
    public Object mcMusicTicker;
    public Object mojangLogo;
    public Object sessionService;
    public Object skinManager;
    public Queue<FutureTask<?>> scheduledTasks;
    public long unnamed1;
    public Thread mcThread;
    public Object modelManager;
    public Object blockRenderDispatcher;
    public boolean running;
    public String debug;
    public boolean unnamed2;
    public boolean unnamed3;
    public boolean unnamed4;
    public boolean renderChunksMany;
    public long debugUpdateTime;
    public int fpsCounter;
    public long previousFrameTime;
    public String debugProfilerName;


    public boolean inWorld() {
        return theWorld != null && thePlayer != null;
    }
}
