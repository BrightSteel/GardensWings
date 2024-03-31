package com.gardensmc.wings.spigot.config;

import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.particle.GardensParticle;
import com.gardensmc.wings.spigot.WingsSpigot;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Objects;

public class SpigotConfig implements WingsConfig {

    private final Configuration configuration;

    public SpigotConfig() {
        configuration = WingsSpigot.getPlugin().getConfig();
    }

    @Override
    public double getBoostMultiplier() {
        ConfigurationSection boostSection = configuration.getConfigurationSection("boost");
        if (boostSection == null) {
            throw new RuntimeException("Invalid boost configuration!");
        }
        return boostSection.getDouble("velocityMultiplier");
    }

    @Override
    public GardensParticle getBoostParticle() {
        ConfigurationSection particleSection = getRequiredSection("boost").getConfigurationSection("particle");
        if (particleSection == null) {
            throw new RuntimeException("Invalid particle in boost configuration!");
        }
        return getGardensParticle(particleSection);
    }

    @Override
    public GardensParticle getLaunchParticle() {
        ConfigurationSection launchSection = getRequiredSection("launch").getConfigurationSection("particle");
        if (launchSection == null) {
            throw new RuntimeException("Invalid particle in launch configuration!");
        }
        return getGardensParticle(launchSection);
    }

    @Override
    public int getBoostCooldown() {
        return getRequiredSection("boost").getInt("cooldown");
    }

    @Override
    public boolean isLaunchSoundEnabled() {
        return Objects.requireNonNull(getRequiredSection("launch").getConfigurationSection("sound")).getBoolean("enabled");
    }

    @Override
    public float getLaunchSoundVolume() {
        return (float) Objects.requireNonNull(getRequiredSection("launch").getConfigurationSection("sound")).getDouble("volume");
    }

    @Override
    public double getLaunchMultiplier() {
        ConfigurationSection launchSection = getRequiredSection("launch");
        return launchSection.getDouble("velocityMultiplier");
    }

    @Override
    public boolean isWingsUnbreakable() {
        return getRequiredSection("wings").getBoolean("unbreakable");
    }

    @Override
    public boolean isWingsEnchanted() {
        return getRequiredSection("wings").getBoolean("enchantGlint");
    }

    @Override
    public String getWingsDisplayName() {
        return getRequiredSection("wings").getString("displayName");
    }

    @Override
    public List<String> getWingsLore() {
        return getRequiredSection("wings").getStringList("lore");
    }

    @Override
    public void reload() {
        WingsSpigot.getPlugin().reloadConfig();
    }

    private GardensParticle getGardensParticle(ConfigurationSection particleSection) {
        return new GardensParticle(
                particleSection.getBoolean("enabled"),
                GardensParticle.ParticleType.valueOf(
                        Objects.requireNonNull(particleSection.getString("particleType"))
                                .toUpperCase()
                ),
                particleSection.getInt("count"),
                particleSection.getDouble("speed")
        );
    }

    private ConfigurationSection getRequiredSection(String key) {
        ConfigurationSection section = configuration.getConfigurationSection(key);
        if (section == null) {
            throw new RuntimeException("Missing " + key + " configuration section!");
        }
        return section;
    }
}
