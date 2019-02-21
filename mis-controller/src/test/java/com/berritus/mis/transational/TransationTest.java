package com.berritus.mis.transational;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class TransationTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void test1(){
        TbStudent student = new TbStudent();
        student.setAge(21);
        student.setStuName("1222");
        demoService.addStudent(student);
    }
}
