package com.gardensmc.wings.fabric.user;

import com.gardensmc.wings.common.user.OnlineUser;
import com.gardensmc.wings.fabric.user.player.FabricPlayer;
import net.minecraft.server.command.ServerCommandSource;

public class FabricUserFactory {

    public static OnlineUser createOnlineUser(ServerCommandSource commandSource) {
        if (commandSource.isExecutedByPlayer() && commandSource.getPlayer() != null) {
            return new FabricPlayer(commandSource.getPlayer());
        } else {
            return new FabricConsoleUser(commandSource);
        }
    }
}
