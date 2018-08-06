package com.hosiang.dev.service;

import com.hosiang.dev.bean.Person;
import com.hosiang.dev.utils.ThreadPool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class RunnableThread {

    //private static final Logger logger = LoggerFactory.getLogger(QueueWorker.class);

   // @Autowired
    //private QueueWorkerService service;

    static int threadCounts = 10;//使用的线程数
    static long sum = 0;


    private void QueueWorker() throws Exception {
        //查询到导出总数
        int count = 200000;
        int starpPage;
        for(starpPage=0; starpPage<count; starpPage=starpPage+10000){
            //List<tk.mybatis.springboot.TEST6.Person> dataList = service.getLastConsumers(starpPage,starpPage+1000);
           // pushRun( dataList);
        }
    }

    private void pushRun(List<Person> dataList) throws Exception {

        if (dataList != null && dataList.size() > 0) {
            ExecutorService pool = ThreadPool.getFixedThreadPool();
            int dataCount = dataList.size();
            int len = dataCount/threadCounts;//分批数
            if(len == 0){
                threadCounts = dataCount;//采用一个线程处理List中的一个元素
                len = 1;//重新平均分割List
            }
            for(int i=0; i<threadCounts; i++){
                final List<Person> subList;
                if(i == threadCounts-1){
                    subList = dataList.subList(i*len, dataCount);
                }else{
                    subList = dataList.subList(i*len, len*(i+1)>dataList.size()?dataList.size():len*(i+1));
                }
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        //调用接口
                        //logger.info("调用接口");
                        //写文件
                        //logger.info("调用接口");
                    }
                };
                pool.execute(run);
            }
            pool.shutdown();
            while (true) {
                if (pool.isTerminated()) {
                    //logger.info("多线程执行结束", Thread.currentThread().getName());
                    break;
                }
                Thread.sleep(200);
            }
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }
    }
}
