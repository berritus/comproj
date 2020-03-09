package com.berritus.mis.dubbo.api;

import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.bean.mybatis.SysFiles;

public interface SysService {
    int insertSysFiles(SysFiles record);
    int deleteSysFiles(Integer fileId);

}
