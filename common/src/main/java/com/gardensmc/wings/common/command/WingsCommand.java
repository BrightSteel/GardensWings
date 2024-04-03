package com.gardensmc.wings.common.command;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.command.exception.InvalidArgumentException;
import com.gardensmc.wings.common.command.exception.InvalidUserException;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.user.OnlineUser;
import com.gardensmc.wings.common.user.player.GardensPlayer;
import com.gardensmc.wings.common.user.player.UserMessageHandler;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class WingsCommand extends GardensCommand {

    public WingsCommand() {
        super("wings", WingsCommandType.GIVE.permission);
    }

    @Override
    public void execute(OnlineUser executor, String[] args) {
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

    private void getWings(OnlineUser executor, String[] args) {
        super.execute(executor, args); // permission check
        // player-only check
        if (!(executor instanceof GardensPlayer gardensPlayer)) {
            throw new InvalidUserException();
        }
        UserMessageHandler userMessageHandler = new UserMessageHandler(gardensPlayer);
        if (gardensPlayer.isChestplateSlotEmpty()) {
            gardensPlayer.addWingsToChestplateSlot();
            userMessageHandler.sendMessage(GardensWings.getLocaleMessage("wings.get.info"));
        } else {
            userMessageHandler.sendError(GardensWings.getLocaleMessage("wings.get.error"));
        }
    }

    private void giveWings(OnlineUser executor, String[] args) {
        // permission check
        if (!executor.hasPermission(WingsCommandType.GIVE.permission)) {
            throw new NoPermissionException(WingsCommandType.GIVE.permission);
        }
        // arg check
        UserMessageHandler executorMessenger = new UserMessageHandler(executor);
        if (args.length < 2) {
            executorMessenger.sendError(GardensWings.getLocaleMessage("wings.give.command.error"));
            return;
        }
        // attempt to find player target
        String username = args[1];
        GardensPlayer player = GardensWings.getGardensServer().getPlayer(username);
        if (player != null) {
            player.addWingsToInventory();
            new UserMessageHandler(player).sendMessage(GardensWings.getLocaleMessage("wings.give.info.receiver", executor.getUsername()));
            executorMessenger.sendMessage(GardensWings.getLocaleMessage("wings.give.info.sender", player.getUsername()));
        } else {
            throw new PlayerNotFoundException(username);
        }
    }

    private void reloadConfig(OnlineUser executor) {
        // permission check
        if (!executor.hasPermission(WingsCommandType.RELOAD.permission)) {
            throw new NoPermissionException(WingsCommandType.RELOAD.permission);
        }
        GardensWings.wingsConfig.reload();
        new UserMessageHandler(executor).sendMessage(GardensWings.getLocaleMessage("wings.config.info.reloaded"));
    }

    @AllArgsConstructor
    enum WingsCommandType {
        GIVE(Permissions.giveWings),
        GET(Permissions.getWings),
        RELOAD(Permissions.reload);

        private final String permission;
    }
}
