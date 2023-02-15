package com.cero.client.sdk.entity;

import com.cero.client.sdk.util.AxisAlignedBB;
import com.cero.client.sdk.util.BlockPos;
import com.cero.client.sdk.util.Vec3;
import com.cero.client.utilities.runtime.Interface;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class Entity extends Interface {

    public Entity(@NotNull Class<?> type, @NotNull Object entity) { super(type, entity); }

    public AxisAlignedBB ZERO_AXIS_ALIGNED_BOUNDING_BOX;

    public int nextEntityID;
    public int entityID;
    public double renderDistanceWeight;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public boolean forceSpawn;
    public Object worldObj; // Don't set this to interface.
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    public boolean isInWeb;
    public boolean isOutsideBorder;
    public boolean isDead;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    public int nextStepDistance;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    public Random rand;
    public int ticksExisted;
    public int fireResistance;
    public int fire;
    public boolean inWater;
    public int hurtResistantTime;
    public boolean firstUpdate;
    public boolean isImmuneToFire;
    public Object dataWatcher;
    public double entityRiderPitchDelta;
    public double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    public boolean inPortal;
    public int portalCounter;
    public int dimension;
    public BlockPos lastPortalPos;
    public Vec3 lastPortalVec;
    public Object teleportDirection; // TODO: Figure out interfacing enumerations
    public boolean invulnerable;
    public UUID entityUniqueID;

}
