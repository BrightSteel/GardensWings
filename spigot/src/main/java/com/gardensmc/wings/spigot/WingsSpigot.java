package com.gardensmc.wings.spigot;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.command.Commands;
import com.gardensmc.wings.common.command.GardensCommand;
import com.gardensmc.wings.common.command.exception.InvalidArgumentException;
import com.gardensmc.wings.common.command.exception.InvalidUserException;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.user.OnlineUser;
import com.gardensmc.wings.common.user.player.UserMessageHandler;
import com.gardensmc.wings.spigot.config.SpigotConfig;
import com.gardensmc.wings.spigot.listener.Listeners;
import com.gardensmc.wings.spigot.user.SpigotUserFactory;
import com.gardensmc.wings.spigot.user.player.SpigotPlayer;
import com.gardensmc.wings.spigot.schedule.SpigotScheduler;
import com.gardensmc.wings.spigot.server.SpigotServer;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class WingsSpigot extends JavaPlugin {

    @Getter
    private static WingsSpigot plugin;
    @Getter // todo - paper implementation that doesn't use this ?
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

        GardensWings.initialize(new SpigotServer(), new SpigotScheduler());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        plugin = null;
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private void registerCommand(GardensCommand gardensCommand) {
        PluginCommand command = this.getCommand(gardensCommand.getName());
        if (command == null) {
            getLogger().log(Level.SEVERE, "Failed to get command: " + gardensCommand.getName());
        } else {
            command.setExecutor(getCommandExecutor(gardensCommand));
            command.setTabCompleter(getTabCompleter(gardensCommand));
        }
    }

    private TabCompleter getTabCompleter(GardensCommand gardensCommand) {
        return (sender, command, label, args) -> {
            if (!(sender instanceof Player player)) {
                return null; // consoles can't tab-complete
            }
            return gardensCommand.getTabCompletion(new SpigotPlayer(player), args);
        };
    }

    private CommandExecutor getCommandExecutor(GardensCommand gardensCommand) {
        return (sender, command, label, args) -> {
            OnlineUser onlineUser = SpigotUserFactory.createOnlineUser(sender);
            UserMessageHandler userMessageHandler = new UserMessageHandler(onlineUser);
            try {
                gardensCommand.execute(onlineUser, args);
            } catch (InvalidUserException e) {
                userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.command.error.invalidUser"));
            } catch (NoPermissionException e) {
                userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.command.error.noPermission"));
            } catch (InvalidArgumentException e) {
                userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.command.error.invalidArguments"));
            } catch (PlayerNotFoundException e) {
                userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.command.error.playerNotFound", e.getUsername()));
            } catch (Exception e) {
                userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.command.error.unknown"));
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
