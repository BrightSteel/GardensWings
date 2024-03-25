package com.gardensmc.wings.spigot.commands;

import com.gardensmc.wings.spigot.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WingsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can summon wings!");
            return true;
        }
        if (!commandSender.hasPermission("gardens.wings")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to grow wings");
            return false;
        }
        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("com/gardensmc/wings/spigot")) {
//            player.getInventory().setChestplate();
            if (player.getInventory().getChestplate() != null) {
                commandSender.sendMessage(ChatColor.RED + "Your chestplate slot is full!");
            }
            else {
                commandSender.sendMessage(ChatColor.AQUA + "You've sprouted wings!");
                player.getInventory().setChestplate(ItemManager.wings);
            }
        }
        return true;
    }
}
