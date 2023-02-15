package com.cero.client.sdk.util;

import com.cero.client.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

// NOTE: Is this even correct??
public class Vec4b extends Interface {

    public Vec4b(@NotNull Class<?> type, @NotNull Object vec4b) { super(type, vec4b); }

    public byte x;
    public byte y;
    public byte z;
    public byte w;

}
