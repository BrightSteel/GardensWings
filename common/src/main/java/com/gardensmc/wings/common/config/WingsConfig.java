package com.gardensmc.wings.common.config;

import com.gardensmc.wings.common.particle.GardensParticle;

import java.util.List;

public interface WingsConfig {
    double getBoostMultiplier();
    GardensParticle getBoostParticle();
    GardensParticle getLaunchParticle();
    boolean isLaunchSoundEnabled();
    float getLaunchSoundVolume();
    double getLaunchMultiplier();
    boolean isWingsUnbreakable();
    String getWingsDisplayName();
    List<String> getWingsLore();
    void reload();
}
