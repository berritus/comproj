//package com.berritus.mis.dubbo.api.impl;
//
//import com.berritus.mis.bean.school.TbStudent;
//import com.berritus.mis.dao.school.TbStudentMapper;
//import com.berritus.mis.dubbo.api.DubboDemoService;
//import com.berritus.mis.service.DemoService;
//import org.springframework.beans.factory.annotation.Autowired;
//
////@Component
////@Service(version = "1.0.1")//
//public class DubboDemoServiceImpl implements DubboDemoService {
//
//    @Autowired
//    private TbStudentMapper studentMapper;
//    @Autowired
//    private DemoService demoService;
//
//    @Override
//    public String helloDubbo() {
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        return "hello dubbo";
//    }
//
//    @Override
//    public TbStudent addStudent(TbStudent student){
//        demoService.addStudent(student);
//        //int i = 10 / 0;
//        return student;
//    }
//}
