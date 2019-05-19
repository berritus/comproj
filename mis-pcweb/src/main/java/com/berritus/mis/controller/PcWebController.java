package com.berritus.mis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.dubbo.api.SysService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PcWebController {
    //final String url = "dubbo://localhost:20880";

    //调远程需要填写URL，不然会调不通
    //@Reference(url = "dubbo://111.230.165.59:20880" version = "1.0.1", timeout = 30000)
    //调本地不需要URL也行
    //@Reference(timeout = 30000, version = "${dubbo.version}")
    @Reference(version = "1.0.2")
    private DubboDemoService dubboDemoService;
    @Reference
    private SysService sysService;
    //@Value("${dubbo.version}")
    private String dubboVersion;

    @RequestMapping("/helloDubbo")
    public String helloDubbo(){
        return dubboDemoService.helloDubbo() + dubboVersion;
    }

    @RequestMapping("/student/add")
    public TbStudent addStudent(@RequestBody TbStudent student){
        return dubboDemoService.addStudent(student);
    }

    @RequestMapping("/session")
    public String session(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return sessionId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sys/files/add")
    public int insertSysFiles(@RequestBody SysFiles record){
        return sysService.insertSysFiles(record);
    }
}
