package com.gardensmc.wings.common.server;

import com.gardensmc.wings.common.user.player.GardensPlayer;

import java.io.File;
import java.util.List;

public interface GardensServer {
    GardensPlayer getPlayer(String username);
    List<? extends GardensPlayer> getOnlinePlayers();
    File getServerFolder();
}
