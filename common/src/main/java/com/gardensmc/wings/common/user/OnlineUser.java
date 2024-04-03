package com.gardensmc.wings.common.user;

public interface OnlineUser {

    String getUsername();
    void sendMessage(String message);
    boolean hasPermission(String permission);
}
