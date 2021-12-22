package com.minecraftgardens.wings;

import com.minecraftgardens.wings.commands.WingsCommands;
import com.minecraftgardens.wings.events.WingsEvents;
import com.minecraftgardens.wings.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Wings extends JavaPlugin {
    @Override
    public void onEnable() {
        ItemManager.init();
        getCommand("wings").setExecutor(new WingsCommands());
        getServer().getPluginManager().registerEvents(new WingsEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Events registered");
    }

    @Override
    public void onDisable() {}
}
