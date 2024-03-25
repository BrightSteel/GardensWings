package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.player.GardensPlayer;

public class PlayerJumpListener extends PlayerListener {

    public PlayerJumpListener(GardensPlayer gardensPlayer) {
        super(gardensPlayer);
    }

    @Override
    public void callListener() {
        if (shouldLaunch()) {
            player.spawnParticle(5);
            player.playSound();
            player.setVelocityMultiplier(GardensWings.wingsConfig.getLaunchMultiplier());
            player.setGliding(true);
        }
    }

    private boolean shouldLaunch() {
        return isLookingUp()
                && player.hasWingsEquipped()
                && player.hasPermission("gardens.wings");
    }

    private boolean isLookingUp() {
        return player.getPitch() <= -50 && player.getPitch() >= -90;
    }
}
