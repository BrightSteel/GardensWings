package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.player.GardensPlayer;

public class PlayerGlideListener extends PlayerListener {

    public PlayerGlideListener(GardensPlayer player) {
        super(player);
    }

    @Override
    public void callListener() {
        if (player.hasWingsEquipped() && !player.hasPermission("gardens.wings")) {
            setShouldCancel(true);
        }
    }
}
