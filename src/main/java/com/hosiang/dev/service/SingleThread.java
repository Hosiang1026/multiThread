package com.hosiang.dev.service;

import java.util.concurrent.TimeUnit;

public class SingleThread {

    public static void test1(int max) throws InterruptedException {
        long t1 = System.currentTimeMillis();
        //等待20毫秒
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println("线程：" + Thread.currentThread().getName() + "----业务处理");

        long t2 = System.currentTimeMillis();
        System.out.println("method1: value=" + max + ",time=" + (t2 - t1));
    }

}
