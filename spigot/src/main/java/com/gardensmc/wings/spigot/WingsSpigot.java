package com.gardensmc.wings.spigot;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.command.Commands;
import com.gardensmc.wings.common.command.GardensCommand;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.player.PlayerMessageHandler;
import com.gardensmc.wings.spigot.config.SpigotConfig;
import com.gardensmc.wings.spigot.listener.Listeners;
import com.gardensmc.wings.spigot.player.SpigotPlayer;
import com.gardensmc.wings.spigot.server.SpigotServer;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class WingsSpigot extends JavaPlugin {

    @Getter
    private static WingsSpigot plugin;
    // todo - paper implementation that doesn't use this ?
    @Getter
    private static BukkitAudiences adventure;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        plugin = this;

        adventure = BukkitAudiences.create(this);

        GardensWings.wingsConfig = new SpigotConfig(); // init config

        Commands.getCommands().forEach(this::registerCommand);
        Listeners.registerListeners();

        GardensWings.initialize(new SpigotServer());
    }

    private void registerCommand(GardensCommand gardensCommand) {
        PluginCommand command = this.getCommand(gardensCommand.getName());
        if (command == null) {
            getLogger().log(Level.SEVERE, "Failed to get command: " + gardensCommand.getName());
        } else {
            command.setExecutor(getCommandExecutor(gardensCommand));
        }
    }

    private CommandExecutor getCommandExecutor(GardensCommand gardensCommand) {
        return (sender, command, label, args) -> {
            // todo - allow consoles to execute command?
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Console cannot run this command!");
                return true;
            }
            SpigotPlayer spigotPlayer = new SpigotPlayer(player);
            PlayerMessageHandler playerMessageHandler = new PlayerMessageHandler(spigotPlayer);
            try {
                gardensCommand.execute(spigotPlayer, args);
            } catch (NoPermissionException e) {
                playerMessageHandler.sendError("You do not have permission to use this command!");
            } catch (PlayerNotFoundException e) {
                playerMessageHandler.sendError(String.format("Cannot find player '%s'", e.getUsername()));
            } catch (Exception e) {
                playerMessageHandler.sendError("An internal server error occurred");
                plugin.getLogger().log(
                        Level.SEVERE,
                        "Failed to run command: " + command.getName(),
                        e
                );
            }
            return true;
        };
    }
}
