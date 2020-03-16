package com.berritus.mis.service.impl;


import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.core.rabbitmq.utils.RabbitMQUtil;
import com.berritus.mis.dao.demo.MeetingRoomApplyDao;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.SysService;
import com.berritus.mis.service.DemoService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    @Qualifier("tranSysService")
    private SysService sysService;
    //@Autowired
    //private MeetingRoomApplyDao meetingRoomApplyDao;
    @Autowired
    private DynamicDemoServiceImpl dynamicDemoService;
    @Autowired
    private RabbitMQUtil rabbitMQUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int sendMsgTest(String sysCode) {
        TbStudent tbStudent = new TbStudent();
        tbStudent.setStuName("2232");
        tbStudent.setAge(13);
        tbStudent.setId(10000);
        //String json = JSON.toJSONString(tbStudent);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        MeetingRoomApplyExt meetingRoomApplyDTO = new MeetingRoomApplyExt();
        meetingRoomApplyDTO.setApplyId(uuid);
        meetingRoomApplyDTO.setApplicationCode("MD_TEST_MEET");

        uuid = UUID.randomUUID().toString().replace("-", "");
        meetingRoomApplyDTO.setRoomId(uuid);
        meetingRoomApplyDTO.setCreator("oa");
        meetingRoomApplyDTO.setState((byte)1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = sdf.format(new Date());
        meetingRoomApplyDTO.setCrtDateStr(strDate);
        meetingRoomApplyDTO.setStartDateStr(strDate);
        meetingRoomApplyDTO.setEndDateStr(strDate);
        //meetingRoomApplyDTO.setModifyDateStr(strDate);
        //meetingRoomApplyDao.insert(meetingRoomApplyDTO);

        Message message = rabbitMQUtil.getMessage(meetingRoomApplyDTO);
        //byte[] body = message.getBody();
        //String str0 = new String(body);
        //System.out.println(str0);

        // direct
        rabbitMQUtil.sendMessage(message, "default_exchange", "test_routing_key");
        // fanout
        //rabbitMQUtil.sendMessage(message, "fanout_exchange0", "");
        // topic
        //rabbitMQUtil.sendMessage(message, "topic_exchange0", "topic.message");
        return 0;
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Transactional
    public void dynamicTest(MeetingRoomApplyExt meetingRoomApplyDTO, String sysCode) {
        //meetingRoomApplyDao.insert(meetingRoomApplyDTO);
        dynamicDemoService.dynamicTest(meetingRoomApplyDTO);
        if (sysCode.equals("MIS_TEST_DB2")) {
            //int i = 10 /0;
        }
        int i = 10 /0;
        //dynamicTest(meetingRoomApplyDTO, "MIS_TEST_DB2", size + 1);
    }

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
