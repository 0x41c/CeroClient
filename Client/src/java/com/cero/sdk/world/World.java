package com.cero.sdk.world;

import com.cero.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class World extends Interface {

    public World(@NotNull Class<?> type, @NotNull Object world) { super(type, world); }

    public static class Identifiers {}

}
