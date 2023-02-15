package com.cero.client.sdk.util;

import com.cero.client.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class Timer extends Interface {

    public Timer(@NotNull Class<?> type, Object instance) {
        super(type, instance);
    }

    public float ticksPerSecond;
    public double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float timerSpeed;
    public float elapsedPartialTicks;
    public long lastSyncSysClock;
    public long lastSyncHRClock;
    public long counter;
    public double timeSyncAdjustment;
}
