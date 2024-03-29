package com.cero.client.sdk.client.entity;

import com.cero.client.sdk.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public class AbstractClientPlayer extends EntityPlayer {
    public AbstractClientPlayer(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public Object playerInfo;
    public Object locationOfCape;
    public long reloadCapeMS;
    public boolean elytraOfCape;
    public String nameClear;
    public Object elytraTexture;
}
