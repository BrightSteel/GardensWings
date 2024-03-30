package com.gardensmc.wings.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;

public class ChatUtil {

    public static Component translateMiniMessage(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static String translateMiniMessageToJson(String message) {
        return JSONComponentSerializer.json().serialize(translateMiniMessage(message));
    }
}
