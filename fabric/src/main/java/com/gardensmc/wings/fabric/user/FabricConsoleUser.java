package com.gardensmc.wings.fabric.user;

import com.gardensmc.wings.common.user.OnlineUser;
import com.gardensmc.wings.common.util.ChatUtil;
import lombok.AllArgsConstructor;
import net.minecraft.server.command.ServerCommandSource;

@AllArgsConstructor
public class FabricConsoleUser implements OnlineUser {

    private ServerCommandSource console;

    @Override
    public String getUsername() {
        return "Console";
    }

    @Override
    public void sendMessage(String message) {
        console.sendMessage(ChatUtil.translateMiniMessage(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
