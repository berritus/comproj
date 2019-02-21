package com.berritus.mis.dubbo.api.impl;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@Service(version = "1.0.1")//
public class DubboDemoServiceImpl implements DubboDemoService {

    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private DemoService demoService;

    @Override
    public String helloDubbo() {
        return "hello dubbo";
    }

    @Override
    public TbStudent addStudent(TbStudent student){
        demoService.addStudent(student);

        return student;
    }
}
