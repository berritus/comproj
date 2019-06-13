package com.berritus.mis.dao;


import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysServiceConfigMapper extends MisDao<SysServiceConfig, Long> {

	List<SysServiceConfig> selectBySysServiceConfig(SysServiceConfig sysServiceConfig);
}