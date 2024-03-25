package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.player.GardensPlayer;

public class PlayerElytraCollisionListener extends PlayerListener {

    public PlayerElytraCollisionListener(GardensPlayer gardensPlayer) {
        super(gardensPlayer);
    }

    @Override
    public void callListener() {
        if (player.hasWingsEquipped() && player.hasPermission(Permissions.noCollisionDamage)) {
            setShouldCancel(true);
        }
    }
}
