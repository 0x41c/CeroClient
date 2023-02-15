package com.cero.client.sdk.util;

import com.cero.client.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class Vec3i extends Interface {

    public Vec3i(@NotNull Class<?> type, @NotNull Object vec3i) { super(type, vec3i); }

    public Object NULL_VECTOR;
    public int x;
    public int y;
    public int z;

}
