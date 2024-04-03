package com.gardensmc.wings.common.user.player;

import com.gardensmc.wings.common.user.OnlineUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMessageHandler {

    private final OnlineUser onlineUser;

    public void sendMessage(String message) {
        onlineUser.sendMessage("<blue>" + message + "</blue>");
    }

    public void sendError(String message) {
        onlineUser.sendMessage("<red>" + message + "</red>");
    }

}
