package com.berritus.mis.controller;


import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.bean.security.TbSysUser;
//import com.berritus.mis.controller.annotation.MisLogger;
import com.berritus.mis.core.cache.redis.RedisService;
import com.berritus.mis.core.controller.annotation.MisLogger;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.dubbo.api.SecurityService;
import com.berritus.mis.dubbo.api.SysService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BlockController {
    private Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    private DubboDemoService dubboDemoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SysService sysService;

    // http://localhost:8081/mis
    @MisLogger
    @RequestMapping("/mis")
    public String helloDubbo(HttpServletRequest request){
        Transaction t = Cat.newTransaction("/mis", "redisService.set");
        String sessionId = "";
        try{
            Cat.logEvent("helloDubbo", "redisService.set", Event.SUCCESS, "ip=${serverIp}");
            Cat.logMetricForCount("metric.key");
            Cat.logMetricForDuration("metric.key", 5);

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

    @MisLogger
    @RequestMapping("/index")
    public String index(){
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
}
