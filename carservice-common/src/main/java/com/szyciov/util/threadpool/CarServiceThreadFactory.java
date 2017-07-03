package com.szyciov.util.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Fisher on 2017/4/12.
 */
public class CarServiceThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    CarServiceThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        this.group = (s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
        this.namePrefix = ("com.szyciov.carservice-" + poolNumber.getAndIncrement() + "-thread-");
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }


}
