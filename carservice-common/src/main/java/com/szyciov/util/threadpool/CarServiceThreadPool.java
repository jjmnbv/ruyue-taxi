package com.szyciov.util.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fisher on 2017/4/12.
 */
public class CarServiceThreadPool {


    private static final int THREAD_MAX_SIZE = 3;
    private static CarServiceThreadPool _instance = new CarServiceThreadPool();
    private static CarServicePool pool = null;
    private boolean hasSetStuck = false;
    private Object lock = new Integer(1);

    private void initInternalPool(int maxSize, int stuckSeconds) {
        if (pool == null) {
            synchronized (this.lock) {
                if (pool == null) {
                    if (maxSize > 0) {
                        pool = new CarServicePool(0, maxSize, 60L, TimeUnit.SECONDS, new SynchronousQueue());
                    } else {
                        pool = new CarServicePool(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue());
                    }
                }
            }
        }
        setStuckSeconds(stuckSeconds);
    }

    private void setStuckSeconds(int secs) {
        if (secs <= 0) {
            return;
        }
        if (this.hasSetStuck) {
            return;
        }
        pool.setStuck(secs * 1000);

        this.hasSetStuck = true;
    }

    public static CarServiceThreadPool getInstance(int stuckSeconds) {
        _instance.initInternalPool(THREAD_MAX_SIZE, stuckSeconds);
        return _instance;
    }

    public static CarServiceThreadPool getInstance() {
        _instance.initInternalPool(THREAD_MAX_SIZE, 0);
        return _instance;
    }

    public static CarServiceThreadPool getInstance(int maxSize, int stuckSeconds) {
        _instance.initInternalPool(maxSize, stuckSeconds);
        return _instance;
    }

    public int getStuckCount() {
        return pool.getStuckCount();
    }

    public int getHoggingCount() {
        return pool.getHoggingCount();
    }

    public int getActiveCount() {
        return pool.getActiveCount();
    }

    public int getCorePoolSize() {
        return pool.getCorePoolSize();
    }

    public int getPoolSize() {
        return pool.getPoolSize();
    }

    public int getMaximumPoolSize() {
        return pool.getMaximumPoolSize();
    }

    public void execute(AsyncTask task) {
        ThreadBase t = new ThreadBase(task);
        pool.execute(t);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    public void shutDown() {
        pool.shutdown();
    }
}
