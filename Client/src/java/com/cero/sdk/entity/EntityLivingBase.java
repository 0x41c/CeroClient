package com.cero.sdk.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class EntityLivingBase extends Entity {
    public EntityLivingBase(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public UUID sprintingSpeedBoostModifierUUID;
    public Object sprintingSpeedBoostModifier;
    public Object attributeMap;
    public Object combatTracker;
    public Map<Integer, Object> activePotionsMap;
    public Object previousEquipment;
    public boolean isSwingInProgress;
    public int swingProgressInt;
    public int arrowHitTimer;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public float prevSwingProgress;
    public float swingProgress;
    public float prevLimbSwingAmount;
    public float limbSwingAmount;
    public float limbSwing;
    public int maxHurtResistantTime;
    public float prevCameraPitch;
    public float cameraPitch;
    public float randomUnused1;
    public float randomUnused2;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    public float rotationYawHead;
    public float prevRotationYawHead;
    public float jumpMovementFactor;
    public Object attackingPlayer;
    public int recentlyHit;
    public boolean dead;
    public int entityAge;
    public float prevOnGroundSpeedFactor;
    public float onGroundSpeedFactor;
    public float movedDistance;
    public float prevMovedDistance;
    public float randomUnused3;
    public int scoreValue;
    public float lastDamage;
    public boolean isJumping;
    public float moveStrafing;
    public float moveForward;
    public float randomYawVelocity;
    public int newPosRotationIncrements;
    public double newPosX;
    public double newPosY;
    public double newPosZ;
    public double newRotationYaw;
    public double newRotationPitch;
    public boolean potionsNeedUpdate;
    public EntityLivingBase entityLivingToAttack;
    public int revengeTimer;
    public EntityLivingBase lastAttacker;
    public int lastAttackerTime;
    public float langMovementFactor;
    public int jumpTicks;
    public float absorptionAmount;
    
}
