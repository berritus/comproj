package com.berritus.mis.dubbo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.DubboDemoService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@org.springframework.stereotype.Service
@Service(version = "1.0.2", interfaceClass = DubboDemoService.class)//
public class DubboTranDemoServiceImpl implements DubboDemoService {
    @Autowired
    private TbStudentMapper studentMapper;

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
}
