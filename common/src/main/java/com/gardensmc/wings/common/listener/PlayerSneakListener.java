package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.cooldown.Cooldowns;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.particle.GardensParticle;
import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.player.PlayerMessageHandler;

public class PlayerSneakListener extends PlayerListener {

    public PlayerSneakListener(GardensPlayer gardensPlayer) {
        super(gardensPlayer);
    }

    @Override
    public void callListener() {
        if (isGlidingWithWingsAndCanBoost() ) {
            if (!Cooldowns.wingsBoostCooldown.hasCooldown(player.getUuid())) {
                // do boost
                player.spawnParticle(GardensWings.wingsConfig.getBoostParticle());
                player.setVelocityMultiplier(GardensWings.wingsConfig.getBoostMultiplier());
                // add cooldown if applicable
                int cooldownInSeconds = GardensWings.wingsConfig.getBoostCooldown();
                if (cooldownInSeconds > 0) {
                    Cooldowns.wingsBoostCooldown.addCooldown(player.getUuid(), cooldownInSeconds);
                }
            } else {
                new PlayerMessageHandler(player).sendError(String.format(
                        "You cannot boost for %s more seconds",
                        Cooldowns.wingsBoostCooldown.getCooldown(player.getUuid()))
                );
            }
        }
    }

    private boolean isGlidingWithWingsAndCanBoost() {
        return player.isGliding() && player.hasWingsEquipped() && player.hasPermission(Permissions.useWingsBoost);
    }
}
