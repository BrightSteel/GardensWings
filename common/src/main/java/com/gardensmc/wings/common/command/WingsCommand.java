package com.gardensmc.wings.common.command;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.Permissions;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.player.PlayerMessageHandler;

public class WingsCommand extends GardensCommand {

    public WingsCommand() {
        super("wings", Permissions.spawnWings);
    }

    @Override
    public void execute(GardensPlayer executor, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("get")) {
            getWings(executor, args);
        } else if (args[0].equalsIgnoreCase("give")) {
            giveWings(executor, args);
        } else {
            new PlayerMessageHandler(executor)
                    .sendError("Invalid arguments! Should be /wings <get> or /wings <give> <player>");
        }
    }

    private void getWings(GardensPlayer executor, String[] args) {
        super.execute(executor, args); // permission check
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
        String permissionOthers = permissionNode + ".others";
        if (!executor.hasPermission(permissionOthers)) {
            throw new NoPermissionException(permissionOthers);
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
}
