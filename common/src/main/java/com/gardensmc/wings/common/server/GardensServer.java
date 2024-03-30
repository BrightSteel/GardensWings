package com.gardensmc.wings.common.server;

import com.gardensmc.wings.common.player.GardensPlayer;

public interface GardensServer {
    GardensPlayer getPlayer(String username);
}
