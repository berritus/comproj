package com.berritus.mis.dao.demo;

import com.berritus.mis.bean.demo.SysTablesMapDTO;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface SysTablesMapDao extends MisDao<SysTablesMapDTO, Long> {

	SysTablesMapDTO selectLastestSysTablesMap(String tableType);

	long getXxxTableCount(String tableName);
}