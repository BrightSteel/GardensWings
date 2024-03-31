package com.gardensmc.wings.fabric.schedule;

import com.gardensmc.wings.common.schedule.Scheduler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FabricScheduler implements Scheduler {

    public static final FabricScheduler INSTANCE = new FabricScheduler();
    private int ticks;
    private final List<ScheduledRepeatingTask> repeatingTasks = new ArrayList<>();

    public void schedule(int ticks, Runnable runnable) {
        repeatingTasks.add(new ScheduledRepeatingTask(ticks, runnable));
    }

    public void tick() {
        ticks++;
        runRepeatingTasks();
    }

    private void runRepeatingTasks() {
        repeatingTasks.forEach(task -> {
            if (ticks % task.getTicks() == 0) {
                task.getRunnable().run();
            }
        });
    }
}
