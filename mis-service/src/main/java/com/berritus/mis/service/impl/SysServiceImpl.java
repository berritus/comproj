package com.berritus.mis.service.impl;

import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.dao.SysFilesMapper;
import com.berritus.mis.dubbo.api.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service("sysService")
@com.alibaba.dubbo.config.annotation.Service(
        interfaceClass = SysService.class, version = "1.0.1", timeout = 10000)
public class SysServiceImpl implements SysService {
    @Autowired
    private SysFilesMapper sysFilesMapper;

    @Override
    public int insertSysFiles(SysFiles record) {
        record.setCrtDate(new Date());
        sysFilesMapper.insert(record);
        //throw new RuntimeException("故意抛错");
        return record.getFileId();
    }

    @Override
    public int deleteSysFiles(Integer fileId){
        sysFilesMapper.deleteByPrimaryKey(fileId);
        return 0;
    }
}
