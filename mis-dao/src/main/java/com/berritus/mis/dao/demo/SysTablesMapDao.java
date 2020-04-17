package com.berritus.mis.dao.demo;

import com.berritus.mis.bean.demo.SysTablesMapDTO;
import com.berritus.mis.core.dao.MisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysTablesMapDao extends MisDao<SysTablesMapDTO, Long> {

	SysTablesMapDTO selectLastestSysTablesMap(String tableType);

	long getXxxTableCount(SysTablesMapDTO sysTablesMapDTO);
	//long getXxxTableCount(String tableName); @Param("tableName") String tableName

	String selectNowUseTableName(String tableType);

	String selectTableNameByIndex(@Param("tableType") String tableType,
								  @Param("indexPos") Long indexPos,
								  @Param("applicationCode") String applicationCode);
}