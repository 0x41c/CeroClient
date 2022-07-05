package com.cero.sdk.util;

import com.cero.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class AxisAlignedBB extends Interface {

    public AxisAlignedBB(@NotNull Class<?> type, @NotNull Object boundingBox) { super(type, boundingBox); }

    // TODO: Figure out a way to guard setting these + implement remote constructors

    public double minX;
    public double minY;
    public double minZ;

    public double maxX;
    public double maxY;
    public double maxZ;

}
