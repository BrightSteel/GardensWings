package com.gardensmc.wings.common.command;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.command.exception.InvalidArgumentException;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.player.PlayerMessageHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class WingsCommand extends GardensCommand {

    public WingsCommand() {
        super("wings", WingsCommandType.GIVE.permission);
    }

    @Override
    public void execute(GardensPlayer executor, String[] args) {
        WingsCommandType type;
        if (args.length == 0) {
            type = WingsCommandType.GET;
        } else {
            try {
                type = WingsCommandType.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidArgumentException();
            }
        }

        switch (type) {
            case GET -> getWings(executor, args);
            case GIVE -> giveWings(executor, args);
            case RELOAD -> reloadConfig(executor);
        }
    }

    @Override
    public List<String> getTabCompletion(GardensPlayer player, String[] args) {
        if (args.length == 1) {
            return Arrays.stream(WingsCommandType.values())
                    .filter(type -> player.hasPermission(type.permission))
                    .map(type -> type.toString().toLowerCase())
                    .toList();
        }
        return super.getTabCompletion(player, args);
    }

    private void getWings(GardensPlayer executor, String[] args) {
        // permission check
        super.execute(executor, args);
        PlayerMessageHandler playerMessageHandler = new PlayerMessageHandler(executor);
        if (executor.isChestplateSlotEmpty()) {
            executor.addWingsToChestplateSlot();
            playerMessageHandler.sendMessage("You've sprouted wings!");
        } else {
            playerMessageHandler.sendError("Your chest plate slot is full!");
        }
    }

    private void giveWings(GardensPlayer executor, String[] args) {
        // permission check
        if (!executor.hasPermission(WingsCommandType.GIVE.permission)) {
            throw new NoPermissionException(WingsCommandType.GIVE.permission);
        }
        // arg check
        PlayerMessageHandler executorMessenger = new PlayerMessageHandler(executor);
        if (args.length < 2) {
            executorMessenger.sendError("Invalid arguments, should be: /wings <give> <player>");
            return;
        }
        // attempt to find player target
        String username = args[1];
        GardensPlayer player = GardensWings.getGardensServer().getPlayer(username);
        if (player != null) {
            player.addWingsToInventory();
            new PlayerMessageHandler(player).sendMessage(executor.getUsername() + " gave you wings!");
            executorMessenger.sendMessage(String.format("Gave %s wings", player.getUsername()));
        } else {
            throw new PlayerNotFoundException(username);
        }
    }

    private void reloadConfig(GardensPlayer executor) {
        // permission check
        if (!executor.hasPermission(WingsCommandType.RELOAD.permission)) {
            throw new NoPermissionException(WingsCommandType.RELOAD.permission);
        }
        GardensWings.wingsConfig.reload();
        new PlayerMessageHandler(executor).sendMessage("Reloaded config!");
    }

    @AllArgsConstructor
    enum WingsCommandType {
        GIVE(Permissions.giveWings),
        GET(Permissions.getWings),
        RELOAD(Permissions.reload);

        private final String permission;
    }
}
