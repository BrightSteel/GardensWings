package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.player.GardensPlayer;

public class PlayerSneakListener extends PlayerListener {

    public PlayerSneakListener(GardensPlayer gardensPlayer) {
        super(gardensPlayer);
    }

    @Override
    public void callListener() {
        if (isGlidingWithWingsAndCanBoost()) {
            player.spawnParticle(5);
            player.setVelocityMultiplier(GardensWings.wingsConfig.getBoostMultiplier());
        }
    }

    private boolean isGlidingWithWingsAndCanBoost() {
        return player.isGliding()
                && player.hasWingsEquipped()
                && player.hasPermission(Permissions.useWingsBoost);
    }
}
