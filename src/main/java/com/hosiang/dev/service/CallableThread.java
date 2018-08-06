package com.hosiang.dev.service;

import com.hosiang.dev.bean.Person;
import com.hosiang.dev.utils.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class CallableThread{

    public static String list2Str(List<Person> list, int nThreads) throws Exception {
        if (list == null || list.isEmpty()) {
            return null;
        }

        StringBuffer ret = new StringBuffer();
        int size = list.size();
        //定长线程池
        ExecutorService executorService = ThreadPool.getFixedThreadPool();

        List<Future<List<Person>>> futures = new ArrayList<Future<List<Person>>>(nThreads);
        //分批数
        int len = size/nThreads;
        if(len == 0){
            //采用一个线程处理List中的一个元素
            nThreads = size;
            //重新平均分割List
            len = 1;
        }

        for ( int i = 0; i < nThreads; i++) {
            final List<Person> subList;
            if(i == nThreads-1){
                subList = list.subList(i*len, size);
            }else{
                subList = list.subList(i*len, len*(i+1)>list.size()?list.size():len*(i+1));
            }

            System.out.println("subList========"+subList.size());

            Callable<List<Person>> task = new Callable<List<Person>>() {
                @Override
                public List<Person> call() throws Exception {
                    for (Person person : subList) {
                        person.setName("业务执行");
                        System.out.println("-----------"+person.getId());
                    }
                    return subList;
                }
            };
            futures.add(executorService.submit(task));
        }

        for (Future<List<Person>> future : futures) {
            //System.out.println("============="+future.get());
        }
        executorService.shutdown();
        System.out.println("执行完毕！");

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                //logger.info("多线程执行结束", Thread.currentThread().getName());
                break;
            }
            Thread.sleep(200);
        }
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        return ret.toString();
    }


    public static void main(String[] args) throws Exception{

        List<Person> list = new ArrayList<Person>();
        for(int i=0;i<7;i++){
            Person person = new Person();
            person.setId("Id-"+i);
            person.setName("Name-"+i);
            list.add(person);
        }

        long start = System.currentTimeMillis();
        list2Str(list,10);
        long end = System.currentTimeMillis();
        System.out.println("多线程全部请求执行完毕，耗时:" + (end - start) + "毫秒");

    }

}
