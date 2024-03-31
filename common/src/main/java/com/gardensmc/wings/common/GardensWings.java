package com.gardensmc.wings.common;

import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.cooldown.CooldownsHandler;
import com.gardensmc.wings.common.schedule.Scheduler;
import com.gardensmc.wings.common.server.GardensServer;
import lombok.Getter;

public class GardensWings {

    @Getter
    private static GardensServer gardensServer;
    public static WingsConfig wingsConfig;
    public static final String WINGS_IDENTIFIER = "gardensWings";

    public static void initialize(GardensServer gardensServer, Scheduler scheduler) {
        GardensWings.gardensServer = gardensServer;
        CooldownsHandler.INSTANCE.scheduleCooldownTick(scheduler);
    }
}
