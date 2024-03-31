package com.gardensmc.wings.spigot.schedule;

import com.gardensmc.wings.common.schedule.Scheduler;
import com.gardensmc.wings.spigot.WingsSpigot;
import org.bukkit.Bukkit;

public class SpigotScheduler implements Scheduler {

    @Override
    public void schedule(int ticks, Runnable runnable) {
        // important - this implementation does not necessarily run on main thread
        Bukkit.getScheduler().runTaskTimer(WingsSpigot.getPlugin(), runnable, 0, ticks);
    }
}
