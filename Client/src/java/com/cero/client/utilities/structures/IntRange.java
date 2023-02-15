package com.cero.client.utilities.structures;

import org.jetbrains.annotations.NonNls;

public class IntRange {

    private int min;
    private int max;

    public IntRange(final int min, final int max) {
        super();
        this.min = min;
        this.max = max;
    }

    @Override
    public final @NonNls String toString() {
        return "IntRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
