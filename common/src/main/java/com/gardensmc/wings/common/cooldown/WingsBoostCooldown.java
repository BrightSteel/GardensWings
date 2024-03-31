package com.gardensmc.wings.common.cooldown;

import com.gardensmc.wings.common.cooldown.type.Cooldown;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class WingsBoostCooldown extends Cooldown {

    private static final HashMap<UUID, Integer> boostCooldownMap = new HashMap<>();

    @Override
    public void addCooldown(UUID key, int cooldownInSeconds) {
        boostCooldownMap.put(key, cooldownInSeconds);
    }

    @Nullable
    @Override
    public Integer getCooldown(UUID key) {
        return boostCooldownMap.get(key);
    }

    @Override
    public boolean hasCooldown(UUID key) {
        return boostCooldownMap.containsKey(key);
    }

    // should be called each second; not too expensive
    @Override
    public void tickCooldowns() {
        Iterator<Map.Entry<UUID, Integer>> itr = boostCooldownMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<UUID, Integer> entry = itr.next();
            int cooldown = entry.getValue();
            if (cooldown <= 1) {
                itr.remove();
            } else {
                entry.setValue(cooldown - 1);
            }
        }
    }
}
