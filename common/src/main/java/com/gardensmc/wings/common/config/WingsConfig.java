package com.gardensmc.wings.common.config;

import java.util.List;

public interface WingsConfig {
    double getBoostMultiplier();
    double getLaunchMultiplier();
    boolean isWingsUnbreakable();
    String getWingsDisplayName();
    List<String> getWingsLore();
    void reload();
}
