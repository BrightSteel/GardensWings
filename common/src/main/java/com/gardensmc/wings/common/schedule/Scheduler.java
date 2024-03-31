package com.gardensmc.wings.common.schedule;

public interface Scheduler {
    void schedule(int ticks, Runnable runnable);
}
