package com.hosiang.dev.service;

import com.hosiang.dev.bean.Person;
import com.hosiang.dev.utils.PoolFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ThreadsTest {
    public static String listToStr(List<String> list){
        StringBuffer str = new StringBuffer();
        Random random = new Random(10);
        Map<Integer,List<String>> group = list.parallelStream().collect(Collectors.groupingBy(e -> random.nextInt(100)));
        group.values().parallelStream().forEach(e -> e.forEach(str::append));
        System.out.println("method1: "+str.toString());
        return str.toString();
    }

    public static String list2Str(List<String> list, final int nThreads) throws Exception {
        if (list == null || list.isEmpty()) {
            return null;
        }

        StringBuffer ret = new StringBuffer();
        int size = list.size();
        ExecutorService executorService = PoolFactory.getThreadPoolExecutor();
        List<Future<String>> futures = new ArrayList<Future<String>>(nThreads);

        for (int i = 0; i < nThreads; i++) {
            final List<String> subList = list.subList(size / nThreads * i, size / nThreads * (i + 1));
            if(subList.size()!=0){
                System.out.println("");
            }
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    StringBuffer sb = new StringBuffer();
                    for (String str : subList) {
                        sb.append(str);
                    }
                    return sb.toString();
                }
            };
            futures.add(executorService.submit(task));
        }

        for (Future<String> future : futures) {
            ret.append(future.get());
        }
        executorService.shutdown();
        System.out.println("method2: "+ret.toString());
        return ret.toString();
    }


    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        for(int i=0;i<2;i++){
            list.add(String.valueOf(i));
        }

        List<Person> plist = new ArrayList<Person>();
        for(int i=0;i<7;i++){
            Person person = new Person();
            person.setId("Id-"+i);
            person.setName("Name-"+i);
            plist.add(person);
        }


        long start = System.currentTimeMillis();
        list2Str(list,10);
        long end = System.currentTimeMillis();
        System.out.println("TEST1多线程全部请求执行完毕，耗时:" + (end - start) + "毫秒");

        long start2 = System.currentTimeMillis();
        listToStr(list);
        long end2 = System.currentTimeMillis();
        System.out.println("TEST2全部请求执行完毕，耗时:" + (end2 - start2) + "毫秒");
    }

}
