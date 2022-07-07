package com.cero.module.category.combat;

import com.cero.module.Module;
import com.cero.module.category.ModuleCategory;
import com.cero.module.settings.ModuleSetting;
import com.cero.module.settings.types.RangeSetting;
import com.cero.sdk.client.entity.EntityPlayerSP;
import com.cero.utilities.structures.Range;

import java.util.List;

public class Velocity extends Module {

    public RangeSetting velocityFactor;

    public Velocity() {
        super(
                "Velocity",
                "Reduces your knockback",
                ModuleCategory.COMBAT,
                true
        );

        settings = List.of(new ModuleSetting[]{
                velocityFactor = new RangeSetting(
                        "velocityFactor",
                        new Range(0.0, 100.0),
                        new Range(40.0, 80.0),
                        1.0
                )
        });
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onUpdate() {
        EntityPlayerSP player = minecraft.thePlayer;
        if (player.maxHurtTime > 0 && player.hurtTime == player.maxHurtTime) {
            double velo = getRandomVelo() / 100D;
            player.motionX *= velo;
            player.motionZ *= velo;
        }
    }

    double getRandomVelo() {
        Range range = velocityFactor.getRange();
        double raw = range.min + Math.random() * (range.max - range.min);
        return (double) Math.round(raw * 100)/100;
    }

}
