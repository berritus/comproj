package com.berritus.mis.dao.demo;

import com.berritus.mis.bean.demo.MeetingRoomApplyDTO;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomApplyDao extends MisDao<MeetingRoomApplyDTO, Long> {

}