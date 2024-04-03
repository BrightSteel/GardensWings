package com.gardensmc.wings.common.user.player;

import com.gardensmc.wings.common.particle.GardensParticle;
import com.gardensmc.wings.common.user.OnlineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class GardensPlayer implements OnlineUser {

    private UUID uuid;
    private String username;

    public abstract boolean isGliding();

    public abstract void setGliding(boolean isGliding);

    public abstract boolean hasWingsEquipped();

    public abstract boolean hasPermission(String permission);

    public abstract void spawnParticle(GardensParticle particle);

    public abstract void playSound(float volume);

    public abstract void setVelocityMultiplier(double multiplier);

    public abstract boolean isChestplateSlotEmpty();

    public abstract void addWingsToChestplateSlot();

    public abstract void addWingsToInventory();

    public abstract void sendMessage(String message);

    public abstract float getPitch();
}
