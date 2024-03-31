package com.gardensmc.wings.common.cooldown;

import com.gardensmc.wings.common.cooldown.type.Cooldown;
import com.gardensmc.wings.common.schedule.Scheduler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CooldownsHandler {

    public static final CooldownsHandler INSTANCE = new CooldownsHandler();
    private static final List<Cooldown> allCooldowns = new ArrayList<>();

    public void addCooldown(Cooldown cooldown) {
        allCooldowns.add(cooldown);
    }

    public void scheduleCooldownTick(Scheduler scheduler) {
        scheduler.schedule(20, this::tickAllCooldowns);
    }

    private void tickAllCooldowns() {
        allCooldowns.forEach(Cooldown::tickCooldowns);
    }
}
