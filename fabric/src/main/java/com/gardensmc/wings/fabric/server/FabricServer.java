package com.gardensmc.wings.fabric.server;

import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.server.GardensServer;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import lombok.AllArgsConstructor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

@AllArgsConstructor
public class FabricServer implements GardensServer {

    private MinecraftServer minecraftServer;

    @Override
    public GardensPlayer getPlayer(String username) {
        ServerPlayerEntity player = minecraftServer.getPlayerManager().getPlayer(username);
        return player == null ? null : new FabricPlayer(player);
    }

    @Override
    public List<? extends GardensPlayer> getOnlinePlayers() {
        return minecraftServer.getPlayerManager().getPlayerList()
                .stream()
                .map(FabricPlayer::new)
                .toList();
    }
}
