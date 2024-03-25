package com.gardensmc.wings.common.player;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerMessageHandler {

    private final GardensPlayer gardensPlayer;

    public void sendMessage(String message) {
        gardensPlayer.sendMessage("[GardensWings] " + message);
    }

    public void sendError(String message) {
        gardensPlayer.sendMessage("[GardensWings] &c" + message);
    }

}
