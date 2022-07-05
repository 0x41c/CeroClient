package com.cero.sdk.util;

import com.cero.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class Vec3 extends Interface  {

    public Vec3(@NotNull Class<?> type, @NotNull Object vec3) { super(type, vec3); }

    public double xCoord;
    public double yCoord;
    public double zCoord;

}
