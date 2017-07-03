package com.szyciov.util.threadpool;

/**
 * Created by Fisher on 2017/4/12.
 */
public class ThreadBase extends Thread {

    private long startTime = 0L;
    private AsyncTask task = null;

    public ThreadBase(AsyncTask task) {
        this.task = task;
    }

    public void run() {
        this.startTime = System.currentTimeMillis();

        try {
            this.task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.startTime = 0L;
    }

    public long getStartTime() {
        return this.startTime;
    }
}
