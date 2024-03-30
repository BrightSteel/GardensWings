package com.gardensmc.wings.common.player;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerMessageHandler {

    private final GardensPlayer gardensPlayer;

    public void sendMessage(String message) {
        gardensPlayer.sendMessage("[GardensWings] <blue>" + message + "</blue>");
    }

    public void sendError(String message) {
        gardensPlayer.sendMessage("[GardensWings] <red>" + message + "</red>");
    }

}
