package com.gardensmc.wings.spigot.config;

import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.spigot.WingsSpigot;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class SpigotConfig implements WingsConfig {

    private final Configuration configuration;

    public SpigotConfig() {
        configuration = WingsSpigot.getPlugin().getConfig();
    }

    @Override
    public double getBoostMultiplier() {
        return configuration.getDouble("boostMultiplier");
    }

    @Override
    public double getLaunchMultiplier() {
        return configuration.getDouble("launchMultiplier");
    }

    @Override
    public boolean isWingsUnbreakable() {
        return Objects.requireNonNull(configuration.getConfigurationSection("wings"))
                .getBoolean("unbreakable");
    }

    @Override
    public void reload() {
        WingsSpigot.getPlugin().reloadConfig();
    }
}
