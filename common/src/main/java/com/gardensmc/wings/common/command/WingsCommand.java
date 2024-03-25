package com.gardensmc.wings.common.command;

import com.gardensmc.wings.common.player.GardensPlayer;

public class WingsCommand extends GardensCommand{

    public WingsCommand() {
        super("wings");
    }

    @Override
    public void execute(GardensPlayer executor, String[] args) {
        super.execute(executor, args);
        if (executor.isChestplateSlotEmpty()) {
            executor.addWingsToChestplateSlot();
            executor.sendMessage("You've sprouted wings!");
        } else {
            executor.sendMessage("Your chestplate slot is full!");
        }
    }
}
