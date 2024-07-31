package com.zeddevstuff.bouncer.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Scheduler
{
    private static List<Map.Entry<Date, Runnable>> tasks = new ArrayList<>();

    private static final Scheduler serverScheduler = new Scheduler();
    public static Scheduler getServerScheduler() {
        return serverScheduler;
    }
    private static final Scheduler clientScheduler = new Scheduler();
    public static Scheduler getClientScheduler() {
        return clientScheduler;
    }

    public void schedule(Runnable runnable, Float delay) {
        tasks.add(Map.entry(new Date(new Date().getTime() + (long)(delay * 1000)), runnable));
    }
    public void tick() {
        for(Map.Entry<Date, Runnable> task : tasks) {
            if(task.getKey().getTime() < new Date().getTime()) {
                task.getValue().run();
                tasks.remove(task);
            }
        }
    }
}
