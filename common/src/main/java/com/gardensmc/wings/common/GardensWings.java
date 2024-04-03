package com.gardensmc.wings.common;

import com.gardensmc.wings.common.config.LocaleConfig;
import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.cooldown.CooldownsHandler;
import com.gardensmc.wings.common.schedule.Scheduler;
import com.gardensmc.wings.common.server.GardensServer;
import lombok.Getter;

import java.util.Arrays;

public class GardensWings {

    @Getter
    private static GardensServer gardensServer;
    public static WingsConfig wingsConfig;
    public static final String WINGS_IDENTIFIER = "gardensWings";
    @Getter
    private static LocaleConfig localeConfig;

    public static void initialize(GardensServer gardensServer, Scheduler scheduler) {
        GardensWings.gardensServer = gardensServer;
        CooldownsHandler.INSTANCE.scheduleCooldownTick(scheduler);
        loadLocale();
    }

    public static void loadLocale() {
        localeConfig = new LocaleConfig(wingsConfig.getLocale());
    }

    public static String getLocaleMessage(String id, String... args) {
        return String.format(localeConfig.getLocaleMessage(id), Arrays.stream(args).toArray());
    }
}
