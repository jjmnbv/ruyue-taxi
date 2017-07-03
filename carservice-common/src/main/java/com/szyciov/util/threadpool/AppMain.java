package com.szyciov.util.threadpool;

/**
 * Created by Fisher on 2017/4/12.
 */
public class AppMain {

    public static void main(String[] args) {
        DemoTask task = new DemoTask();

        while(true) {
            CarServiceThreadPool.getInstance().execute(task);
        }

    }

}
