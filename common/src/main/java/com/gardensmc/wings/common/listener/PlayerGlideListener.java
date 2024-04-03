package com.gardensmc.wings.common.listener;

import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.listener.type.PlayerListener;
import com.gardensmc.wings.common.user.player.GardensPlayer;

public class PlayerGlideListener extends PlayerListener {

    public PlayerGlideListener(GardensPlayer player) {
        super(player);
    }

    @Override
    public void callListener() {
        if (player.hasWingsEquipped() && !player.hasPermission(Permissions.useWingsGlide)) {
            setShouldCancel(true);
        }
    }
}
