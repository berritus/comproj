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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BlockController {
    @Autowired
    private DubboDemoService dubboDemoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SysService sysService;

    @MisLogger
    @RequestMapping("/")
    public String helloDubbo(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        redisService.set("sessionId", sessionId, (long)90);
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
