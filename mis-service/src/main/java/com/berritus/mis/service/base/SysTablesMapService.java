package com.berritus.mis.service.base;

import com.berritus.mis.bean.demo.SysTablesMapDTO;
import com.berritus.mis.core.component.service.MisCoreService;
import com.berritus.mis.dao.demo.SysTablesMapDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/26
 */
@Service
public class SysTablesMapService extends MisCoreService<SysTablesMapDao, SysTablesMapDTO, Long> {


	@Autowired
	private SysTablesMapDao sysTablesMapDao;

	public String selectNowUseTableName() {
		return null;
	}

	public String selectTableNameByIndex(String tableType, Long indexPos, String applicationCode) {
		return sysTablesMapDao.selectTableNameByIndex(tableType, indexPos, applicationCode);
	}
}
