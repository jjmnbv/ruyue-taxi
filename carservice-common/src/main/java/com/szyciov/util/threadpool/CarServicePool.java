package com.szyciov.util.threadpool;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fisher on 2017/4/12.
 */
public class CarServicePool extends ThreadPoolExecutor {
    public static final Logger log = Logger.getLogger(CarServicePool.class);


    private Map<Long, ThreadBase> threads = new ConcurrentHashMap();
    private int stuckCount = 0;
    private int hoggingCount = 0;
    private int logNo = 0;
    private long stuckSeconds = 600000L;
    private CheckThread checkThread = null;

    public CarServicePool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new CarServiceThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        this.checkThread = new CheckThread();
        this.checkThread.setName("carservice-threadpool-check-thread");
        this.checkThread.start();
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if ((r instanceof ThreadBase)) {
            this.threads.put(Long.valueOf(((ThreadBase) r).getId()), (ThreadBase) r);
        }
        super.beforeExecute(t, r);
    }

    @Override
    public void shutdown() {
        stopCheck();
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        stopCheck();
        return super.shutdownNow();
    }

    public String dumpThreads() {
        return null;
    }

    public void setStuck(long stuckTimeMs) {
        this.stuckSeconds = stuckTimeMs;
    }

    public int getStuckCount() {
        return this.stuckCount;
    }

    public int getHoggingCount() {
        return this.hoggingCount;
    }

    private void check() {
        Iterator iter_threads = this.threads.entrySet().iterator();
        long currentTime = System.currentTimeMillis();
        while (iter_threads.hasNext()) {
            Map.Entry entry = (Map.Entry) iter_threads.next();
            ThreadBase td = (ThreadBase) entry.getValue();
            long startTime = td.getStartTime();
            long interval = currentTime - startTime;
            if (interval > this.stuckSeconds) {
                this.stuckCount += 1;
            }
            if ((interval > this.stuckSeconds / 2L) && (interval < this.stuckSeconds)) {
                this.hoggingCount += 1;
            }
        }
        this.logNo += 1;

        if (this.logNo == 3) {
            if (this.hoggingCount > 0) {
                log.warn("................Task hogging的个数为：" + this.hoggingCount + "个", null);
            }
            if (this.stuckCount > 0) {
                log.warn("................Task stuck的个数为：" + this.stuckCount + "个", null);
            }
            if (this.threads.size() >= getMaximumPoolSize()) {
                log.warn("................CarServiceThreadPool线程池内线程数目为：" + this.threads.size() + "个，已超过线程池最大线程数目！");
            }
            this.logNo = 0;
        }
        this.stuckCount = 0;
        this.hoggingCount = 0;
    }


    public void stopCheck() {
        try {
            this.checkThread.setStop();
            this.checkThread.interrupt();
        } catch (Exception e) {

        }
    }


    class CheckThread extends Thread {
        private boolean toStop = false;

        public void setStop() {
            this.toStop = true;
        }

        CheckThread() {
        }

        @Override
        public void run() {
            while (!this.toStop) {
                try {
                    sleep(10000L);
                } catch (InterruptedException e) {
                    return;
                }
            }
            CarServicePool.this.check();
        }
    }
}
