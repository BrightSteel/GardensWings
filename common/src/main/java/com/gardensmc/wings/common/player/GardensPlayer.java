package com.gardensmc.wings.common.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class GardensPlayer {

    private UUID uuid;
    private String username;

    public abstract boolean isGliding();

    public abstract void setGliding(boolean isGliding);

    public abstract boolean hasWingsEquipped();

    public abstract boolean hasPermission(String permission);

    public abstract void spawnParticle(int count);

    public abstract void playSound();

    public abstract void setVelocityMultiplier(double multiplier);

    public abstract boolean isChestplateSlotEmpty();

    public abstract void addWingsToChestplateSlot();

    public abstract void sendMessage(String message);

    public abstract float getPitch();
}