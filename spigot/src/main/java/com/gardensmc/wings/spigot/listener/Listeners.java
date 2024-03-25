package com.gardensmc.wings.spigot.listener;

import com.gardensmc.wings.spigot.WingsSpigot;
import com.gardensmc.wings.spigot.listener.type.SpigotListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused") // registered listeners look unused
public class Listeners {

    @Getter
    private static final Set<Listener> listeners = new HashSet<>();

    private static final SpigotListener playerListener = new PlayerListener();

    public static void addListenerToRegistry(Listener listener) {
        listeners.add(listener);
    }

    public static void registerListeners() {
        listeners.forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(
                listener, WingsSpigot.getPlugin())
        );
    }
}
