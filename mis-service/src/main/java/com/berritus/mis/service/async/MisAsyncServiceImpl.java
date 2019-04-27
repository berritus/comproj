package com.berritus.mis.service.async;

import com.berritus.mis.service.IMisAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
//@Async
public class MisAsyncServiceImpl implements IMisAsyncService {
    private Logger logger = LoggerFactory.getLogger(MisAsyncServiceImpl.class);

    @Override
    @Async
    public Future<String> getActionData() {
        long t1 = System.currentTimeMillis();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis();
        return new AsyncResult<>("getActionData use time " + (t2 - t1) + " ms");
    }

    @Override
    @Async
    public Future<String> getActionData2() {
        long t1 = System.currentTimeMillis();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis();
        return new AsyncResult<>("getActionData2 use time " + (t2 - t1) + " ms");
    }
}
