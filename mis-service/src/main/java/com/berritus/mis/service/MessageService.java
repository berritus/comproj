package com.berritus.mis.service;

import com.berritus.mis.bean.demo.MeetingRoomApplyExt;

public interface MessageService {
    int sendConfirmMsg(String requestMsg);

    int sendMsgTest();

    int dynamicTest(String sysCode);

}
