package com.berritus.mis.service;


import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.bean.school.TbStudent;

public interface DemoService {
    String helloDubbo();
    TbStudent addStudent(TbStudent student);
    void dynamicTest(MeetingRoomApplyExt meetingRoomApplyDTO, String sysCode);
    int sendMsgTest(String sysCode);
}
