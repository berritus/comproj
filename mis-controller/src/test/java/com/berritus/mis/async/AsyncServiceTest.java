package com.berritus.mis.async;

import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.service.IMisAsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class AsyncServiceTest {

    @Autowired
    private IMisAsyncService misAsyncService;

    @Test
    public void test1() throws Exception{
        long t0 = System.currentTimeMillis();
        System.out.println("========================================== doing test1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long t1 = System.currentTimeMillis();
        Future<String> task1 = misAsyncService.getActionData();
        Future<String> task2 = misAsyncService.getActionData2();
        long t2 = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String str1 = task1.get();
        String str2 = task2.get();
        System.out.println("========================================== str1 " + str1);
        System.out.println("========================================== str2 " + str2);
        long t3 = System.currentTimeMillis();

        System.out.println("========================================== async doing using time " + (t2 - t1) + "ms");
        System.out.println("========================================== total using time " + (t3 - t0) + "ms");
    }
}
