package com.gardensmc.wings.spigot.user;

import com.gardensmc.wings.common.user.OnlineUser;
import lombok.AllArgsConstructor;
import org.bukkit.command.ConsoleCommandSender;

@AllArgsConstructor
public class SpigotConsoleUser implements OnlineUser {

    private ConsoleCommandSender consoleCommandSender;

    @Override
    public String getUsername() {
        return "Console";
    }

    @Override
    public void sendMessage(String message) {
        consoleCommandSender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true; // console always has perms
    }
}
