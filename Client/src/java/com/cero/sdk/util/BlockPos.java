package com.cero.sdk.util;

import org.jetbrains.annotations.NotNull;

public class BlockPos extends Vec3i {

    public BlockPos(@NotNull Class<?> type, @NotNull Object blockPos) { super(type, blockPos); }

    public Object ORIGIN; // Don't set this to Interface

    public int NUM_X_BITS;
    public int NUM_Z_BITS;
    public int NUM_Y_BITS;

    public int Y_SHIFT;
    public int X_SHIFT;

    public long X_MASK;
    public long Y_MASK;
    public long Z_MASK;

}
