package com.berritus.mis.service.impl;


import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.SysService;
import com.berritus.mis.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    @Qualifier("tranSysService")
    private SysService sysService;

    @Override
    public String helloDubbo() {
        return "hello dubbo";
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TbStudent addStudent(TbStudent student){
        studentMapper.insert(student);

        String mongoId = UUID.randomUUID().toString();
        SysFiles record = new SysFiles();
        record.setContentType("Propagation.REQUIRED");
        record.setFileName("Propagation.REQUIRED");
        record.setMongoFileId(mongoId);
        record.setUseType(1);
        record.setOwner(100000);
        sysService.insertSysFiles(record);
        //int i = 1 /0;
        return student;
    }
}
