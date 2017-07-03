package com.szyciov.util.threadpool;

import java.util.Random;

/**
 * Created by Fisher on 2017/4/12.
 */
public class DemoTask implements AsyncTask {

    @Override
    public void run() {
        Random rand = new Random(25);
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(rand.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
