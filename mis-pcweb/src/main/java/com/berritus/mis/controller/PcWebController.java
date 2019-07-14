package com.berritus.mis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.berritus.mis.bean.common.ResultVO;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.common.utils.MisHttpUtil;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.dubbo.api.SysService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public ModelAndView addStudent(@RequestBody TbStudent student){
        Map<String, Object> map = new HashMap<>();
        dubboDemoService.addStudent(student);

        ResultVO resultVO = MisHttpUtil.getResultVO(true, 0, "成功");
        map.put("result", resultVO);
        return new ModelAndView(new MappingJackson2JsonView(), map);
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
