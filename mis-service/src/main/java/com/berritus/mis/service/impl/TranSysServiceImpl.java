package com.berritus.mis.service.impl;

import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.dao.SysFilesMapper;
import com.berritus.mis.dubbo.api.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("tranSysService")
public class TranSysServiceImpl implements SysService {
    @Autowired
    private SysFilesMapper sysFilesMapper;

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int insertSysFiles(SysFiles record) {
        record.setCrtDate(new Date());
        sysFilesMapper.insert(record);
        int i = 1 /0;
        return record.getFileId();
    }

    @Override
    public int deleteSysFiles(Integer fileId) {
        return 0;
    }
}
