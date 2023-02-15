package com.cero.client.sdk.client.entity;

import org.jetbrains.annotations.NotNull;

public class EntityOtherPlayerMP extends AbstractClientPlayer {

    public EntityOtherPlayerMP(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public boolean isItemInUse;
    public int otherPlayerMPPosRotationIncrements;
    public double otherPlayerMPX;
    public double otherPlayerMPY;
    public double otherPlayerMPZ;
    public double otherPlayerMPYaw;
    public double otherPlayerMPPitch;

}
