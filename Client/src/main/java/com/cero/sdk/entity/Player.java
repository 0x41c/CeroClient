package com.cero.sdk.entity;

import com.cero.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

public class Player extends Interface {

    public Player(@NotNull Class<?> type, @NotNull Object instance) { super(type, instance); }

    // EntityLivingBase 67

    public int getPlayerHurtTime() {
        return getFieldAt(76);
    }

    public void setPlayerHurtTime(int newPlayerHurtTime) {
        setFieldAt(76, newPlayerHurtTime);
    }

    public int getMaxHurtTime() {
        return getFieldAt(77);
    }

    // Entity is offset 124

    public double getMotionX() {
        return getFieldAt(139);
    }

    public void setMotionX(double newMotionX) {
        setFieldAt(139, newMotionX);
    }

    public double getMotionY() {
        return getFieldAt(140);
    }

    public void setMotionY(double newMotionY) {
        setFieldAt(140, newMotionY);
    }

    public double getMotionZ() {
        return getFieldAt(141);
    }

    public void setMotionZ(double newMotionZ) {
        setFieldAt(141, newMotionZ);
    }
}
