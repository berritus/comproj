package com.berritus.mis.dubbo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.berritus.mis.service.base.CallProcedureService;
import com.berritus.mis.service.base.SplitTableService;
import com.berritus.mis.service.base.SysTablesMapService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// @Transactional
@org.springframework.stereotype.Service
@Service(version = "1.0.2", interfaceClass = DubboDemoService.class)//
public class DubboTranDemoServiceImpl implements DubboDemoService {
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private CallProcedureService callProcedureService;
    @Autowired
    private SplitTableService splitTableService;
    @Autowired
    private SysTablesMapService sysTablesMapService;

    @Override
    public TbStudent getStudent(TbStudent student) {
        //String tableName = sysTablesMapService.selectTableNameByIndex("student", (long)student.getId(), "MD_TEST_OA");

        return studentMapper.selectByPrimaryKey(student.getId());
    }

    @Override
    public String callProduce(String sysCode) {
        callProcedureService.callProcedureCreateMqMessageInfoXx("mq_message_info_1", sysCode);
        return "success";
    }

    @Override
    public String helloDubbo() {
        Transaction t = Cat.newTransaction("helloDubbo", "do...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str = "hello world";
        t.setStatus(Transaction.SUCCESS);
        t.complete();
        return str;
    }

    @Override
    public TbStudent addStudent(TbStudent student) {
        studentMapper.insert(student);
        //throw new RuntimeException("错误啦");
        return student;
    }

    @Override
    public TbStudent addStudent() {
        TbStudent student = new TbStudent();
        //student.setId(10002);
        student.setAge(28);
        student.setStuName("aaaaaa");

        //splitTableService.checkStudentIsNeedSplit();
        studentMapper.insert(student);
        //throw new RuntimeException("错误啦");
//        student.setId(5070);
//        student.setStuName("99222233339");
//        studentMapper.updateByPrimaryKey(student);
        return student;
    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
