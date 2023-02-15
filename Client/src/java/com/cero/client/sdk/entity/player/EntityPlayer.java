package com.cero.client.sdk.entity.player;

import com.cero.client.sdk.entity.EntityLivingBase;
import com.cero.client.sdk.util.BlockPos;
import org.jetbrains.annotations.NotNull;

public class EntityPlayer extends EntityLivingBase {

    public EntityPlayer(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public Object inventory;
    public Object enderChest;
    public Object inventoryContainer;
    public Object currentOpenContainer;
    public Object foodStats;
    public int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;
    public int xpCoolDown;
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;
    public boolean sleeping;
    public BlockPos playerLocation;
    public int sleepTimer;
    public float renderOffsetX;
    public float renderOffsetY;
    public float renderOffsetZ;
    public BlockPos spawnChunk;
    public boolean spawnForced;
    public BlockPos startMineCartRidingCoordinate;
    public Object capabilities;
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    public int xpSeed;
    public Object itemInUse;
    public int itemInUseCount;
    public float speedOnGround;
    public float speedInAir;
    public int lastXPSound;
    public Object gameProfile;
    public boolean hasReducedDebug;
    public Object fishEntity;
}
