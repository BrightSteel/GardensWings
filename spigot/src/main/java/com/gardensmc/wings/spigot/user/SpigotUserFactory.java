package com.gardensmc.wings.spigot.user;

import com.gardensmc.wings.common.user.OnlineUser;
import com.gardensmc.wings.spigot.user.player.SpigotPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpigotUserFactory {

    public static OnlineUser createOnlineUser(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            return new SpigotPlayer(player);
        } else if (commandSender instanceof ConsoleCommandSender console) {
            return new SpigotConsoleUser(console);
        } else {
            throw new RuntimeException("Unknown commandSender provided!");
        }
    }
}
