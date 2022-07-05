package com.cero.sdk.client.entity;

import com.cero.sdk.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public class AbstractClientPlayer extends EntityPlayer {
    public AbstractClientPlayer(@NotNull Class<?> type, @NotNull Object entity) {
        super(type, entity);
    }

    public Object playerInfo;
}
