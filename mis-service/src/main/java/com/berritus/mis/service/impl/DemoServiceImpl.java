package com.berritus.mis.service.impl;


import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.demo.MeetingRoomApplyDao;
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
public class DemoServiceImpl {

    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    @Qualifier("tranSysService")
    private SysService sysService;
    //@Autowired
    //private MeetingRoomApplyDao meetingRoomApplyDao;
    @Autowired
    private DynamicDemoServiceImpl dynamicDemoService;

    //@Override
    //@Transactional(propagation = Propagation.REQUIRED)
    public void dynamicTest(MeetingRoomApplyExt meetingRoomApplyDTO, String sysCode) {
        //meetingRoomApplyDao.insert(meetingRoomApplyDTO);
        dynamicDemoService.dynamicTest(meetingRoomApplyDTO);
        if (sysCode.equals("MIS_TEST_DB2")) {
            //int i = 10 /0;
        }
    }

    //@Override
    public String helloDubbo() {
        return "hello dubbo";
    }

    //@Override
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
