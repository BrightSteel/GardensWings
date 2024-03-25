package com.gardensmc.wings.spigot.listener.type;

import com.gardensmc.wings.spigot.listener.Listeners;
import org.bukkit.event.Listener;

public class SpigotListener implements Listener {

    public SpigotListener() {
        Listeners.addListenerToRegistry(this);
    }
}
