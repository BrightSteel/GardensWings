package com.gardensmc.wings.spigot.server;

import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.server.GardensServer;
import com.gardensmc.wings.spigot.player.SpigotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class SpigotServer implements GardensServer {

    @Override
    public GardensPlayer getPlayer(String username) {
        Player player = Bukkit.getPlayer(username);
        return player == null ? null : new SpigotPlayer(player);
    }

    @Override
    public List<? extends GardensPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(SpigotPlayer::new)
                .toList();
    }
}
