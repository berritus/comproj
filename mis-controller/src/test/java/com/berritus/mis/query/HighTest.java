package com.berritus.mis.query;

import com.berritus.mis.bean.mybatis.MisOrder;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.core.cache.redis.RedisService;
import com.berritus.mis.dubbo.api.OrderService;
import com.berritus.mis.dubbo.api.QrySysService;
import com.berritus.mis.dubbo.api.SysService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class HighTest {
    @Autowired
    private QrySysService qrySysService;
    private final int MAX_COUNT = 1;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    @Autowired
    @Qualifier("sysService")
    private SysService sysService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    private class ThreadTest implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Random random = new Random();
//            int rannum = random.nextInt(3);
//            try {
//                Thread.sleep(rannum * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println(Thread.currentThread().getName() + " do task");
            SysFiles bean = new SysFiles();
            bean.setFileId(1000004);
            SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
            System.out.println(sysFiles.getMongoFileId());
        }
    }

    private class ThreadTest3 implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            Random random = new Random();
//            int rannum = random.nextInt(3);
//            try {
//                Thread.sleep(rannum * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.currentThread().getName() + " do task");
            SysFiles sysFiles = qrySysService.qrySysFiles3(1000006);
            System.out.println(sysFiles.getMongoFileId());
        }
    }

    public class ThreadOrder implements Runnable{

        @Override
        public void run() {
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            MisOrder order = new MisOrder();
            order.setCustId((int)Thread.currentThread().getId());
            order.setProdId(10000);
            orderService.insertMisOrder2(order);
//            String orderCode = orderService.genOrderCode();
//            System.out.println(orderCode);
//            try {
//                orderService.formatDate();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Test
    public void test6(){
        String value = UUID.randomUUID().toString();
        redisService.set("10002", value, (long)20);
        //redisTemplate.opsForValue().set("10001", value);

        SysFiles bean = new SysFiles();
        bean.setFileId(1000004);
        SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
        redisService.set(sysFiles.getFileId() + "", sysFiles, (long)30);

        SysFiles bean2 = (SysFiles)redisService.get(sysFiles.getFileId() + "");
        System.out.println(bean2.getFileName());

        boolean flag = redisService.exists(bean2.getFileId() + "");
        if(flag){
            System.out.println("exists key");
        }else{
            System.out.println("not exists key");
        }

        redisService.remove(bean2.getFileId() + "");

        flag = redisService.exists(bean2.getFileId() + "");
        if(flag){
            System.out.println("exists key");
        }else{
            System.out.println("not exists key");
        }
        //redisTemplate.opsForValue().set(bean.getFileId() + "", bean);
    }

    @Test
    public void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        ThreadOrder threadOrder = new ThreadOrder();
        for (int i = 0; i < 300; i++){
            executorService.submit(threadOrder);
        }

        Thread.sleep(30000);
    }

    @Test
    public void test4() throws InterruptedException {
        ThreadOrder threadOrder = new ThreadOrder();
        for (int i = 0; i < 300; i++){
            new Thread(threadOrder).start();
        }

        countDownLatch.countDown();

        Thread.sleep(30000);
    }

    @Test
    public void test1(){
        for (int i = 0; i < MAX_COUNT; i++){
            SysFiles bean = new SysFiles();
            bean.setFileId(1000004);
            SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
            System.out.println(sysFiles.getMongoFileId());
        }
    }

    @Test
    public void test2() throws InterruptedException {
        //new RedisDBSwitchUtil().switchDb(0);
        //redisDBSwitchUtil.switchDb(0);
        ThreadTest threadTest = new ThreadTest();
        for(int i = 0; i < MAX_COUNT; i++){
            new Thread(threadTest).start();
        }

        countDownLatch.countDown();

        //redisDBSwitchUtil.switchDb(3);
//        ThreadTest3 threadTest3 = new ThreadTest3();
//        for(int i = 0; i < MAX_COUNT; i++){
//            new Thread(threadTest3).start();
//        }
//        countDownLatch.countDown();
//        System.out.println("main thread run finished ...");

        Thread.currentThread().sleep(10000);

//        LettuceConnectionFactory redisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
//        int orgIndex = redisConnectionFactory.getDatabase();
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        int index = lettuceConnectionFactory.getDatabase();
        System.out.println(index);
    }

    @Test
    public void testIns(){
        String uuid = UUID.randomUUID().toString();
        SysFiles sysFiles = new SysFiles();
        sysFiles.setContentType("text/plain");
        sysFiles.setFileName("wall.jpg");
        sysFiles.setMongoFileId(uuid);
        sysFiles.setOwner(9527);
        sysFiles.setUseType(2);
        sysService.insertSysFiles(sysFiles);
    }

    @Test
    public void testExp(){
        ExpressionParser parser = new SpelExpressionParser();
//        String str = (String)parser.parseExpression("'insert into student'").getValue();
//        System.out.println(str);

        //'fileId' + #args[1]
        //EvaluationContext context = new StandardEvaluationContext();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String args[] = new String[] {"hello", "world"};
        System.out.println(args[1]);
        context.setVariable("args", args);
        String str1 = parser.parseExpression("'fileId:' + #args[1]").getValue(context, String.class);
        System.out.println(str1);
    }

    @Test
    public void testTime(){
        long times = System.currentTimeMillis();
        System.out.println(times);
    }
}
