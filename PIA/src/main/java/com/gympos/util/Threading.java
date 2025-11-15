package com.gympos.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threading {
    private static ExecutorService executor;

    public static void init() {
        executor = Executors.newFixedThreadPool(10);
    }

    public static void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    public static void submitTask(Runnable task) {
        if (executor != null) {
            executor.submit(task);
        }
    }
}
