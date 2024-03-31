package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.player.GardensPlayer;

public class PlayerSneakJumpListener extends PlayerListener {

    public PlayerSneakJumpListener(GardensPlayer gardensPlayer) {
        super(gardensPlayer);
    }

    @Override
    public void callListener() {
        WingsConfig wingsConfig = GardensWings.wingsConfig;
        if (shouldLaunch()) {
            player.spawnParticle(wingsConfig.getLaunchParticle());
            if (wingsConfig.isLaunchSoundEnabled()) {
                player.playSound(wingsConfig.getLaunchSoundVolume());
            }
            player.setVelocityMultiplier(wingsConfig.getLaunchMultiplier());
            player.setGliding(true);
        }
    }

    private boolean shouldLaunch() {
        return isLookingUp()
                && player.hasWingsEquipped()
                && player.hasPermission(Permissions.useWingsLaunch);
    }

    private boolean isLookingUp() {
        return player.getPitch() <= -50 && player.getPitch() >= -90;
    }
}
