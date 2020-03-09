package com.berritus.mis.controller;


import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.bean.security.TbSysUser;
import com.berritus.mis.core.cache.redis.IRedisService;
import com.berritus.mis.core.component.annotation.MisLogger;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.dubbo.api.SecurityService;
import com.berritus.mis.dubbo.api.SysService;
import com.berritus.mis.service.MessageService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//import com.berritus.mis.controller.annotation.MisLogger;

@RestController
public class BlockController {
    private static final Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    private DubboDemoService dubboDemoService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SysService sysService;
    @Autowired
    private MessageService messageService;

    // http://localhost:8081/mis
    @MisLogger
    @RequestMapping("/mis")
    public String helloDubbo(HttpServletRequest request){
        Transaction t = Cat.newTransaction("/mis", "redisService.set");
        String sessionId = "";
        try{
            //Cat.logEvent("helloDubbo", "redisService.set", Event.SUCCESS, "ip=${serverIp}");
            //Cat.logMetricForCount("metric.key");
            //Cat.logMetricForDuration("metric.key", 5);

            sessionId = request.getSession().getId();
            redisService.set("sessionId", sessionId, (long)90);
            logger.warn("helloDubbo");
            t.setStatus(Transaction.SUCCESS);

        }catch (Exception e){
            t.setStatus(e);
            Cat.logError(e);
        } finally{
            t.complete();
        }

        return dubboDemoService.helloDubbo() + ",sessionId = " + sessionId;
    }

    // http://localhost:8081/index
    @MisLogger
    @RequestMapping("/index")
    public String index(){
        logger.info("index");
        return "index";
    }

    @MisLogger
    @RequestMapping("/session")
    public String getSession(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        String session = (String) redisService.get("sessionId");
        return "sessionId1 = " + sessionId + ",sessionId redis = " + session;
    }

    //localhost:8081/student/add
    @MisLogger
    @RequestMapping("/student/add")
    public TbStudent addStudent(@RequestBody TbStudent student){
        return dubboDemoService.addStudent(student);
    }

    @MisLogger
    @RequestMapping("/sys/user/qry")
    public TbSysUser selectByUserName(String userName){
        return securityService.selectByUserName(userName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sys/files/add")
    public int insertSysFiles(@RequestBody SysFiles record){
        return sysService.insertSysFiles(record);
    }


    // http://localhost:8081/rabbitmq
    @MisLogger
    @RequestMapping("/rabbitmq")
    public String rabbitmq(){
        // messageService.sendConfirmMsg("rabbitmq");
        messageService.sendMsgTest();
        return "rabbitmq";
    }

    // http://localhost:8081/dynamicTest
    @MisLogger
    @RequestMapping("/dynamicTest")
    public String dynamicTest(){
        messageService.dynamicTest("MIS_TEST_DB");
        return "dynamicTest";
    }

    // VM Args: -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
    // http://localhost:8081/testCatThreadOOM
    @GetMapping("/testCatThreadOOM")
    public void testCatThreadOOM() {
        int n = 0;
        while(true) {
            n++;
            try {
                Transaction t = Cat.newTransaction("/testCatThreadOOM", "redisService.set");
                int i = 1 /0;
                //t.setStatus(Transaction.SUCCESS);
                //t.complete();
            } catch (Exception e) {
                System.out.println("n = " + n + e);
            }
        }
    }
}
