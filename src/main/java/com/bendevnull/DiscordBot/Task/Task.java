package com.bendevnull.DiscordBot.Task;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Task extends TimerTask {

    private long delay;
    private long interval;
    private boolean repeat;

    private Timer timer;

    public Task(long delay) {
        this.delay = delay;
        this.repeat = false;
    }

    public Task(long delay, boolean repeat) {
        this.delay = delay;
        this.repeat = repeat;
        this.interval = delay;
    }

    public Task(long delay, long interval, boolean repeat) {
        this.delay = delay;
        this.interval = interval;
        this.repeat = repeat;
    }

    public void start() {
        this.timer = new Timer();
        if (this.repeat == true) {
            timer.schedule(this, this.delay, this.interval);
        } else {
            timer.schedule(this, this.delay);
        }
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }
}