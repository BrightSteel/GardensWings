package com.gardensmc.wings.fabric.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduledRepeatingTask {
    private int ticks;
    private Runnable runnable;
}
