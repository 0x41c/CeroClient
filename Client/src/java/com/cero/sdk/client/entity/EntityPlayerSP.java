package com.cero.sdk.client.entity;

import org.jetbrains.annotations.NotNull;

public class EntityPlayerSP extends AbstractClientPlayer {

    public EntityPlayerSP(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public Object sendQueue;
    public Object statWriter;
    public double lastReportedPosX;
    public double lastReportedPosY;
    public double lastReportedPosZ;
    public float lastReportedPitch;
    public boolean serverSneakState;
    public boolean serverSprintState;
    public int positionUpdateTicks;
    public boolean hasValidHealth;
    public String clientBrand;
    public Object movementInput;
    public Object minecraft; // Don't set this to interface.
    public int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    public int horseJumpPowerCounter;
    public float horseJumpPower;
    public float timeInPortal;
    public float prevTimeInPortal;

}
