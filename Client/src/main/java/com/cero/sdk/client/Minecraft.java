package com.cero.sdk.client;

import com.cero.Client;
import com.cero.sdk.entity.Player;
import com.cero.sdk.world.World;
import com.cero.utilities.runtime.Interface;
import com.cero.utilities.runtime.RuntimeField;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;

public class Minecraft extends Interface {

    public static class Identifiers {
        public final static String LOGGER                           = "logger";
        public final static String MOJANG_PNG_LOCATION              = "location_mojang_png";
        public final static String IS_RUNNING_ON_MAC                = "is_running_on_mac";
        public final static String MEMORY_RESERVE                   = "memory_reserve";
        public final static String MAC_DISPLAY_MODES                = "mac_display_modes";
        public final static String FILE_RESOURCE_PACKS              = "file_resource_packs";
        public final static String TWITCH_DETAILS                   = "twitch_details";
        public final static String PROFILE_PROPERTIES               = "profile_properties";
        public final static String CURRENT_SERVER_DATA              = "current_server_data";
        public final static String RENDER_ENGINE                    = "render_engine";
        public final static String MINECRAFT_INSTANCE               = "the_minecraft";
        public final static String PLAYER_CONTROLLER                = "player_controller";
        public final static String IS_FULLSCREEN                    = "is_fullscreen";
        public final static String ENABLE_GL_ERROR_TRACKING         = "enable_gl_tracking";
        public final static String HAS_CRASHED                      = "has_crashed";
        public final static String CRASH_REPORTER                   = "crash_reporter";
        public final static String DISPLAY_WIDTH                    = "display_width";
        public final static String DISPLAY_HEIGHT                   = "display_height";
        public final static String CONNECTED_TO_REALMS              = "connected_to_realms";
        public final static String GAME_TIMER                       = "game_timer";
        public final static String USAGE_SNOOPER                    = "usage_snooper";
        public final static String THE_WORLD                        = "the_world";
        public final static String RENDER_GLOBAL                    = "render_global";
        public final static String RENDER_MANAGER                   = "render_manager";
        public final static String RENDER_ITEM                      = "render_item";
        public final static String ITEM_RENDERER                    = "item_renderer";
        public final static String THE_PLAYER                       = "the_player";
        public final static String RENDER_VIEW_ENTITY               = "render_view_entity";
        public final static String POINTED_ENTITY                   = "pointed_entity";
        public final static String EFFECT_RENDERER                  = "effect_renderer";
        public final static String SESSION                          = "session";
        public final static String IS_GAME_PAUSED                   = "is_game_paused";
        public final static String FONT_RENDERER                    = "font_renderer";
        public final static String STANDARD_GALACTIC_FONT_RENDERER  = "standard_galactic_font_renderer";
        public final static String CURRENT_SCREEN                   = "current_screen";
        public final static String LOADING_SCREEN                   = "loading_screen";
        public final static String ENTITY_RENDERER                  = "entity_renderer";
        public final static String LEFT_CLICK_COUNTER               = "left_click_counter";
        public final static String TEMP_DISPLAY_WIDTH               = "temp_display_width";
        public final static String TEMP_DISPLAY_HEIGHT              = "temp_display_height";
        public final static String THE_INTEGRATED_SERVER            = "the_integrated_server";
        public final static String GUI_ACHIEVEMENT                  = "gui_achievement";
        public final static String IN_GAME_GUI                      = "in_game_gui";
        public final static String SKIP_RENDER_WORLD                = "skip_render_world";
        public final static String OBJECT_MOUSE_OVER                = "object_mouse_over";
        public final static String GAME_SETTINGS                    = "game_settings";
        public final static String MOUSE_HELPER                     = "mouse_helper";
        public final static String MC_DATA_DIR                      = "mc_data_dir";
        public final static String FILE_ASSETS                      = "file_assets";
        public final static String LAUNCHED_VERSION                 = "launched_version";
        public final static String PROXY                            = "proxy";
        public final static String SAVE_LOADER                      = "save_loader";
        public final static String DEBUG_FPS                        = "debug_fps";
        public final static String RIGHT_CLICK_DELAY_TIMER          = "right_click_delay_timer";
        public final static String SERVER_NAME                      = "server_name";
        public final static String SERVER_PORT                      = "server_port";
        public final static String IN_GAME_HAS_FOCUS                = "in_game_has_focus";
        public final static String SYSTEM_TIME                      = "system_time";
        public final static String JOIN_PLAYER_COUNTER              = "join_player_counter";
        public final static String FRAME_TIMER                      = "frame_timer";
        public final static String START_NANO_TIME                  = "start_nano_time";
        public final static String JVM_64_BIT                       = "jvm_64_bit";
        public final static String IS_DEMO                          = "is_demo";
        public final static String MY_NETWORK_MANAGER               = "my_network_manager";
        public final static String INTEGRATED_SERVER_IS_RUNNING     = "integrated_server_is_running";
        public final static String MC_PROFILER                      = "mc_profiler";
        public final static String DEBUG_CRASH_KEY_PRESS_TIME       = "debug_crash_key_press_time";
        public final static String MC_RESOURCE_MANAGER              = "mc_resource_manager";
        public final static String METADATA_SERIALIZER              = "metadata_serializer";
        public final static String DEFAULT_RESOURCE_PACKS           = "default_resource_packs";
        public final static String MC_DEFAULT_RESOURCE_PACK         = "mc_default_resource_pack";
        public final static String MC_RESOURCE_PACK_REPOSITORY      = "mc_resource_pack_repository";
        public final static String MC_LANGUAGE_MANAGER              = "mc_language_manager";
        public final static String STREAM                           = "stream";
        public final static String FRAME_BUFFER_MC                  = "frame_buffer_mc";
        public final static String TEXTURE_MAP_BLOCKS               = "texture_map_blocks";
        public final static String MC_SOUND_HANDLER                 = "mc_sound_handler";
        public final static String MC_MUSIC_TICKER                  = "mc_music_ticker";
        public final static String MOJANG_LOGO                      = "mojang_logo";
        public final static String SESSION_SERVICE                  = "session_service";
        public final static String SKIN_MANAGER                     = "skin_manager";
        public final static String SCHEDULED_TASKS                  = "scheduled_tasks";
        public final static String UNNAMED_1                        = "unnamed_1";
        public final static String MC_THREAD                        = "mc_thread";
        public final static String MODULE_MANAGER                   = "model_manager";
        public final static String BLOCK_RENDER_DISPATCHER          = "block_render_dispatcher";
        public final static String RUNNING                          = "running";
        public final static String DEBUG                            = "debug";
        public final static String UNNAMED_2                        = "unnamed_2";
        public final static String UNNAMED_3                        = "unnamed_3";
        public final static String UNNAMED_4                        = "unnamed_4";
        public final static String RENDER_CHUNKS_MANY               = "render_chunks_many";
        public final static String DEBUG_UPDATE_TIME                = "debug_update_time";
        public final static String FPS_COUNTER                      = "fps_counter";
        public final static String PREVIOUS_FRAME_TIME              = "previous_frame_time";
        public final static String DEBUG_PROFILER_NAME              = "debug_profiler_name";
    }

    public Minecraft(@NotNull Object instance) throws ClassNotFoundException {
        super(Class.forName(Client.shared.getMCName()), instance);
    };

    public Minecraft(@NotNull Class<?> type, @NotNull Object instance) {
        super(type, instance);
    }

    @RuntimeField(offset = 0, identifier = Identifiers.LOGGER)
    public Object logger;

    @RuntimeField(offset = 1, identifier = Identifiers.MOJANG_PNG_LOCATION)
    public Object mojangPngLocation;

    @RuntimeField(offset = 2, identifier = Identifiers.IS_RUNNING_ON_MAC)
    public boolean isRunningOnMac;

    @RuntimeField(offset = 3, identifier = Identifiers.MEMORY_RESERVE)
    public byte[] memoryReserve;

    @RuntimeField(offset = 4, identifier = Identifiers.MAC_DISPLAY_MODES)
    public List<Object> macDisplayModes;

    @RuntimeField(offset = 5, identifier = Identifiers.FILE_RESOURCE_PACKS)
    public File fileResourcePacks;

    @RuntimeField(offset = 6, identifier = Identifiers.TWITCH_DETAILS)
    public Object twitchDetails;

    @RuntimeField(offset = 7, identifier = Identifiers.PROFILE_PROPERTIES)
    public Object profileProperties;

    @RuntimeField(offset = 8, identifier = Identifiers.CURRENT_SERVER_DATA)
    public Object currentServerData;

    @RuntimeField(offset = 9, identifier = Identifiers.RENDER_ENGINE)
    public Object renderEngine;

    @RuntimeField(offset = 10, identifier = Identifiers.MINECRAFT_INSTANCE)
    public Minecraft theMinecraft;

    @RuntimeField(offset = 11, identifier = Identifiers.PLAYER_CONTROLLER)
    public Object playerController;

    @RuntimeField(offset = 12, identifier = Identifiers.IS_FULLSCREEN)
    public boolean isFullscreen;

    @RuntimeField(offset = 13, identifier = Identifiers.ENABLE_GL_ERROR_TRACKING)
    public boolean enableGLErrorTracking;

    @RuntimeField(offset = 14, identifier = Identifiers.HAS_CRASHED)
    public boolean hasCrashed;

    @RuntimeField(offset = 15, identifier = Identifiers.CRASH_REPORTER)
    public Object crashReporter;

    @RuntimeField(offset = 16, identifier = Identifiers.DISPLAY_WIDTH)
    public int displayWidth;

    @RuntimeField(offset = 17, identifier = Identifiers.DISPLAY_HEIGHT)
    public int displayHeight;

    @RuntimeField(offset = 18, identifier = Identifiers.CONNECTED_TO_REALMS)
    public boolean connectedToRealms;

    @RuntimeField(offset = 19, identifier = Identifiers.GAME_TIMER)
    public Object gameTimer;

    @RuntimeField(offset = 20, identifier = Identifiers.USAGE_SNOOPER)
    public Object usageSnooper;

    @RuntimeField(offset = 21, identifier = Identifiers.THE_WORLD)
    public World theWorld;

    @RuntimeField(offset = 22, identifier = Identifiers.RENDER_GLOBAL)
    public Object renderGlobal;

    @RuntimeField(offset = 23, identifier = Identifiers.RENDER_MANAGER)
    public Object renderManager;

    @RuntimeField(offset = 24, identifier = Identifiers.RENDER_ITEM)
    public Object renderItem;

    @RuntimeField(offset = 25, identifier = Identifiers.ITEM_RENDERER)
    public Object itemRenderer;

    @RuntimeField(offset = 26, identifier = Identifiers.THE_PLAYER)
    public Player thePlayer;

    @RuntimeField(offset = 27, identifier = Identifiers.RENDER_VIEW_ENTITY)
    public Object renderViewEntity;

    @RuntimeField(offset = 28, identifier = Identifiers.POINTED_ENTITY)
    public Object pointedEntity;

    @RuntimeField(offset = 29, identifier = Identifiers.EFFECT_RENDERER)
    public Object effectRenderer;

    @RuntimeField(offset = 30, identifier = Identifiers.SESSION)
    public Object session;

    @RuntimeField(offset = 31, identifier = Identifiers.IS_GAME_PAUSED)
    public boolean isGamePaused;

    @RuntimeField(offset = 32, identifier = Identifiers.FONT_RENDERER)
    public Object fontRenderer;

    @RuntimeField(offset = 33, identifier = Identifiers.STANDARD_GALACTIC_FONT_RENDERER)
    public Object standardGalacticFontRenderer;

    @RuntimeField(offset = 34, identifier = Identifiers.CURRENT_SCREEN)
    public Object currentScreen;

    @RuntimeField(offset = 35, identifier = Identifiers.LOADING_SCREEN)
    public Object loadingScreen;

    @RuntimeField(offset = 36, identifier = Identifiers.ENTITY_RENDERER)
    public Object entityRenderer;

    @RuntimeField(offset = 37, identifier = Identifiers.LEFT_CLICK_COUNTER)
    public int leftClickCounter;

    @RuntimeField(offset = 38, identifier = Identifiers.TEMP_DISPLAY_WIDTH)
    public int tempDisplayWidth;

    @RuntimeField(offset = 39, identifier = Identifiers.TEMP_DISPLAY_HEIGHT)
    public int tempDisplayHeight;

    @RuntimeField(offset = 40, identifier = Identifiers.THE_INTEGRATED_SERVER)
    public Object theIntegratedServer;

    @RuntimeField(offset = 41, identifier = Identifiers.GUI_ACHIEVEMENT)
    public Object guiAchievement;

    @RuntimeField(offset = 42, identifier = Identifiers.IN_GAME_GUI)
    public Object inGameGUI;

    @RuntimeField(offset = 43, identifier = Identifiers.SKIP_RENDER_WORLD)
    public boolean skipRenderWorld;

    @RuntimeField(offset = 44, identifier = Identifiers.OBJECT_MOUSE_OVER)
    public Object objectMouseOver;

    @RuntimeField(offset = 45, identifier = Identifiers.GAME_SETTINGS)
    public Object gameSettings;

    @RuntimeField(offset = 46, identifier = Identifiers.MOUSE_HELPER)
    public Object mouseHelper;

    @RuntimeField(offset = 47, identifier = Identifiers.MC_DATA_DIR)
    public File mcDataDir;

    @RuntimeField(offset = 48, identifier = Identifiers.FILE_ASSETS)
    public File fileAssets;

    @RuntimeField(offset = 49, identifier = Identifiers.LAUNCHED_VERSION)
    public String launchedVersion;

    @RuntimeField(offset = 50, identifier = Identifiers.PROXY)
    public Object proxy;

    @RuntimeField(offset = 51, identifier = Identifiers.SAVE_LOADER)
    public Object saveLoader;

    @RuntimeField(offset = 52, identifier = Identifiers.DEBUG_FPS)
    public int debugFPS;

    @RuntimeField(offset = 53, identifier = Identifiers.RIGHT_CLICK_DELAY_TIMER)
    public int rightClickDelayTimer;

    @RuntimeField(offset = 54, identifier = Identifiers.SERVER_NAME)
    public String serverName;

    @RuntimeField(offset = 55, identifier = Identifiers.SERVER_PORT)
    public int serverPort;

    @RuntimeField(offset = 56, identifier = Identifiers.IN_GAME_HAS_FOCUS)
    public boolean inGameFocus;

    @RuntimeField(offset = 57, identifier = Identifiers.SYSTEM_TIME)
    public long systemTime;

    @RuntimeField(offset = 58, identifier = Identifiers.JOIN_PLAYER_COUNTER)
    public int joinPlayerCounter;

    @RuntimeField(offset = 59, identifier = Identifiers.FRAME_TIMER)
    public Object frameTimer;

    @RuntimeField(offset = 60, identifier = Identifiers.START_NANO_TIME)
    public long startNanoTime;

    @RuntimeField(offset = 61, identifier = Identifiers.JVM_64_BIT)
    public boolean jvm64Bit;

    @RuntimeField(offset = 62, identifier = Identifiers.IS_DEMO)
    public boolean isDemo;

    @RuntimeField(offset = 63, identifier = Identifiers.MY_NETWORK_MANAGER)
    public Object myNetworkManager;

    @RuntimeField(offset = 64, identifier = Identifiers.INTEGRATED_SERVER_IS_RUNNING)
    public boolean integratedServerIsRunning;

    @RuntimeField(offset = 65, identifier = Identifiers.MC_PROFILER)
    public Object mcProfiler;

    @RuntimeField(offset = 66, identifier = Identifiers.DEBUG_CRASH_KEY_PRESS_TIME)
    public long debugCrashKeyPressTime;

    @RuntimeField(offset = 67, identifier = Identifiers.MC_RESOURCE_MANAGER)
    public Object mcResourceManager;

    @RuntimeField(offset = 68, identifier = Identifiers.METADATA_SERIALIZER)
    public Object metadataSerializer;

    @RuntimeField(offset = 69, identifier = Identifiers.DEFAULT_RESOURCE_PACKS)
    public List<Object> defaultResourcePacks;

    @RuntimeField(offset = 70, identifier = Identifiers.MC_DEFAULT_RESOURCE_PACK)
    public Object mcDefaultResourcePack;

    @RuntimeField(offset = 71, identifier = Identifiers.MC_RESOURCE_PACK_REPOSITORY)
    public Object mcResourcePackRepository;

    @RuntimeField(offset = 72, identifier = Identifiers.MC_LANGUAGE_MANAGER)
    public Object mcLanguageManager;

    @RuntimeField(offset = 73, identifier = Identifiers.STREAM)
    public Object stream;

    @RuntimeField(offset = 74, identifier = Identifiers.FRAME_BUFFER_MC)
    public Object frameBufferMC;

    @RuntimeField(offset = 75, identifier = Identifiers.TEXTURE_MAP_BLOCKS)
    public Object textureMapBlocks;

    @RuntimeField(offset = 76, identifier = Identifiers.MC_SOUND_HANDLER)
    public Object mcSoundHandler;

    @RuntimeField(offset = 77, identifier = Identifiers.MC_MUSIC_TICKER)
    public Object mcMusicTicker;

    @RuntimeField(offset = 78, identifier = Identifiers.MOJANG_LOGO)
    public Object mojangLogo;

    @RuntimeField(offset = 79, identifier = Identifiers.SESSION_SERVICE)
    public Object sessionService;

    @RuntimeField(offset = 80, identifier = Identifiers.SKIN_MANAGER)
    public Object skinManager;

    @RuntimeField(offset = 81, identifier = Identifiers.SCHEDULED_TASKS)
    public Queue<FutureTask<?>> scheduledTasks;

    @RuntimeField(offset = 82, identifier = Identifiers.UNNAMED_1)
    public long unnamed1;

    @RuntimeField(offset = 83, identifier = Identifiers.MC_THREAD)
    public Thread mcThread;

    @RuntimeField(offset = 84, identifier = Identifiers.MODULE_MANAGER)
    public Object modelManager;

    @RuntimeField(offset = 85, identifier = Identifiers.BLOCK_RENDER_DISPATCHER)
    public Object blockRenderDispatcher;

    @RuntimeField(offset = 86, identifier = Identifiers.RUNNING)
    public boolean running;

    @RuntimeField(offset = 87, identifier = Identifiers.DEBUG)
    public String debug;

    @RuntimeField(offset = 88, identifier = Identifiers.UNNAMED_2)
    public boolean unnamed2;

    @RuntimeField(offset = 89, identifier = Identifiers.UNNAMED_3)
    public boolean unnamed3;

    @RuntimeField(offset = 90, identifier = Identifiers.UNNAMED_4)
    public boolean unnamed4;

    @RuntimeField(offset = 91, identifier = Identifiers.RENDER_CHUNKS_MANY)
    public boolean renderChunksMany;

    @RuntimeField(offset = 92, identifier = Identifiers.DEBUG_UPDATE_TIME)
    public long debugUpdateTime;

    @RuntimeField(offset = 93, identifier = Identifiers.FPS_COUNTER)
    public int fpsCounter;

    @RuntimeField(offset = 94, identifier = Identifiers.PREVIOUS_FRAME_TIME)
    public long previousFrameTime;

    @RuntimeField(offset = 95, identifier = Identifiers.DEBUG_PROFILER_NAME)
    public String debugProfilerName;


    public boolean inWorld() {
        return theWorld != null && thePlayer != null;
    }
}
