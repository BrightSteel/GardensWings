package com.gardensmc.wings.common.server;

import com.gardensmc.wings.common.player.GardensPlayer;

import java.util.List;

public interface GardensServer {
    GardensPlayer getPlayer(String username);
    List<? extends GardensPlayer> getOnlinePlayers();
}
