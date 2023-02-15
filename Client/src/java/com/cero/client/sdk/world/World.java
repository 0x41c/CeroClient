package com.cero.client.sdk.world;

import com.cero.client.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class World extends Interface {

    public World(@NotNull Class<?> type, @NotNull Object world) { super(type, world); }

    public static class Identifiers {}

}
