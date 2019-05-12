package com.berritus.mis.fork.join;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/12
 */
public class ForkJoinTest {

    class CountTask extends RecursiveTask<Long> {
        private final static int THRESHOLD = 500000;

        private int start;
        private int end;
        private List<Long> listNum;

        public CountTask(int start, int end, List<Long> listNum) {
            this.start = start;
            this.end = end;
            this.listNum = listNum;
        }

        @Override
        protected Long compute() {
            long sum = 0;

            if (end - start < THRESHOLD) {
                for (int i = start; i < end; i++) {
                    sum += listNum.get(i);
                }
            } else {
                int mid = (start + end) / 2;

                CountTask task1 = new CountTask(start, mid, listNum);
                CountTask task2 = new CountTask(mid, end, listNum);
                CountTask.invokeAll(task1, task2);

                sum = task1.join() + task2.join();
            }

            return sum;
        }
    }

    class ThreadPrintNum implements Runnable {
        private Long num;
        private CountDownLatch latch;

        public ThreadPrintNum(Long num, CountDownLatch latch) {
            this.num = num;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(num);
            latch.countDown();
        }
    }

    @Test
    public void test1() throws Exception {
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();

        int end = 1000000;
        List<Long> listNum = new ArrayList<>();

        for (long i = 0; i < end; i++) {
            listNum.add(i);
        }

        CountTask task = new CountTask(0, end, listNum);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> submit = pool.submit(task);
        long sum = submit.get();
        t2 = System.currentTimeMillis();

        System.out.println("sum = " + sum + ",use time = " + (t2 - t1));
    }

    @Test
    public void test2() {
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();

        int end = 1000000;
        long sum = 0;
        for (long i = 0; i < end; i++) {
            sum += i;
        }
        t2 = System.currentTimeMillis();
        System.out.println("sum = " + sum + ",use time = " + (t2 - t1));
    }


    @Test
    public void test3() throws Exception {
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();
        int end = 1000000;
        List<Long> listNum = new ArrayList<>();

        for (long i = 0; i < end; i++) {
            listNum.add(i);
        }

        CountDownLatch latch = new CountDownLatch(end);

        int processorsNum = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(2 * processorsNum);

        for (int i = 0; i < end; i++) {
            ThreadPrintNum thread = new ThreadPrintNum(listNum.get(i), latch);
            service.submit(thread);
        }

        service.shutdown();
        latch.await();
        t2 = System.currentTimeMillis();
        System.out.println("use time = " + (t2 - t1));
    }

    @Test
    public void test4() {
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();
        int end = 1000000;
        List<Long> listNum = new ArrayList<>();

        for (long i = 0; i < end; i++) {
            listNum.add(i);
        }

        for (int i = 0; i < end; i++) {
            System.out.println(listNum.get(i));
        }

        t2 = System.currentTimeMillis();
        System.out.println("use time = " + (t2 - t1));
    }

    @Test
    public void getCurrentThreads() {

        long start = System.currentTimeMillis();

        //prenum = prenum-1;// 任务数量 进度扣减
        //uses += use;

        long now = System.currentTimeMillis() / 1000;
        //if(now > tempcount){// 每秒输出一次
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;//已使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;//总共可使用内存
        int cpu = Runtime.getRuntime().availableProcessors();//可用cpu逻辑处理器

        //System.out.printf("第%s秒: ", now-allstart/1000);
        //int ts = (nums-prenum);//每秒事务数
        //System.out.printf("每秒处理数:%s ", ts);
        //System.out.printf("平均耗时:%s ", ts==0?0:uses/ts);
        //System.out.printf("进度:%s ", nums);
        //System.out.printf("剩余:%s毫秒 ", nums*ts);
        System.out.printf("可用内存:%sm ", freeMemory);
        System.out.printf("可用总内存:%sm \n", totalMemory);
        System.out.printf("cpu数量:%s \n", cpu);
        //tempcount = now;
        //nums = prenum;
        //uses =0;
        //}

        //logstime += System.currentTimeMillis()-start;// 日志耗时累计

        // 当任务执行完了以后 计算总耗时
//        if(prenum==0){
//            long alluse = System.currentTimeMillis()-allstart;
//            System.out.printf("总耗时%s毫秒,其中日志耗时%s毫秒\n",alluse,logstime);
//            System.exit(0);
//        }

    }
}
