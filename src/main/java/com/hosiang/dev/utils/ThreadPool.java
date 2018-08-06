package com.hosiang.dev.utils;

import java.util.concurrent.*;

/**
 * 线程池创建
 */
public class ThreadPool {

    /**
     * 核心线程数量
     */
    private static final int coreThreadCount = 40;

    /**
     * 可缓存线程池：
     *
     * 1.线程数无限制
     * 2.有空闲线程则复用空闲线程，若无空闲线程则新建线程
     * 3.一定程序减少频繁创建/销毁线程，减少系统开销
     * @return
     */
    public static ExecutorService getCachedThreadPool(){

        SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<Runnable>();
        ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
                TimeUnit.SECONDS, synchronousQueue);

        return cachedThreadPool;
    }

    /**
     * 定长线程池：
     *
     * 1.可控制线程最大并发数（同时执行的线程数）
     * 2.超出的线程会在队列中等待
     * @return
     */
    public static ExecutorService getFixedThreadPool(){

        BlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(coreThreadCount, 200, 2000, TimeUnit.SECONDS,
        linkedBlockingQueue);

        return fixedThreadPool;
    }

}
