package com.gardensmc.wings.common.cooldown.type;

import com.gardensmc.wings.common.cooldown.CooldownsHandler;

import java.util.UUID;

public abstract class Cooldown {

    public Cooldown() {
        CooldownsHandler.INSTANCE.addCooldown(this);
    }

    public abstract void addCooldown(UUID key, int cooldownInSeconds);

    public abstract boolean hasCooldown(UUID key);

    public abstract Integer getCooldown(UUID key);

    public abstract void tickCooldowns();
}
