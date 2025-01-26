package com.skquery.skquery.util;

import org.bukkit.Bukkit;

public abstract class CancellableBukkitTask implements Runnable {

    private int taskId;

    public final void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public final void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
