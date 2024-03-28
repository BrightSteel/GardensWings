package com.gardensmc.wings.fabric.config;

import com.gardensmc.wings.common.config.WingsConfig;

public class FabricConfig implements WingsConfig {
    // todo make reference actual config
    @Override
    public double getBoostMultiplier() {
        return 2.5;
    }

    @Override
    public double getLaunchMultiplier() {
        return 3.0;
    }

    @Override
    public boolean isWingsUnbreakable() {
        return true;
    }

    @Override
    public void reload() {

    }
}
