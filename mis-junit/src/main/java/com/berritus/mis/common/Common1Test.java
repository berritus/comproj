package com.berritus.mis.common;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/29
 */
public class Common1Test {

    class ThreadTest implements Runnable {

        private CountDownLatch countDownLatch;

        public ThreadTest(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stringTest();
        }
    }

    private void stringTest(){
        String str = new String("123").intern();
         synchronized(str) {
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println(Thread.currentThread().getName() + " running");
         }
    }

    @Test
    public void test1() throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            ThreadTest threadTest = new ThreadTest(countDownLatch);

            new Thread(threadTest).start();
        }

        countDownLatch.countDown();

        Thread.sleep(100000);
    }


}
