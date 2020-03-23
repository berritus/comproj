package com.berritus.mis.service.impl;

import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.service.DemoSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/23
 */
@Service
public class DemoSecondServiceImpl implements DemoSecondService {
    @Autowired
    private DynamicDemoServiceImpl dynamicDemoService;

    @Override
    @Transactional
    public void dynamicTest(MeetingRoomApplyExt meetingRoomApplyDTO, String sysCode) {
        dynamicDemoService.dynamicTest(meetingRoomApplyDTO);
    }
}
