package com.berritus.mis.service.impl;

import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.dao.demo.MeetingRoomApplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/9
 */
@Service
public class DynamicDemoServiceImpl {
    @Autowired
    private MeetingRoomApplyDao meetingRoomApplyDao;

    public void dynamicTest(MeetingRoomApplyExt meetingRoomApplyDTO) {
        meetingRoomApplyDao.insert(meetingRoomApplyDTO);
    }
}
