package com.berritus.mis.service.base;

import com.berritus.mis.bean.demo.SysTablesMapDTO;
import com.berritus.mis.dao.demo.SysTablesMapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-04-08
 */
@Service
public class SplitTableService {
	@Autowired
	private SysTablesMapDao sysTablesMapDao;


	public void checkStudentIsNeedSplit() {
		SysTablesMapDTO sysTablesMap = sysTablesMapDao.selectLastestSysTablesMap("student");
		if (sysTablesMap == null) {
			return;
		}

		String nowTableName = sysTablesMap.getMapTableName();
		int tableNum = sysTablesMap.getTableNum();

		SysTablesMapDTO sysTablesMapDTO = new SysTablesMapDTO();
		sysTablesMapDTO.setApplicationCode("MIS_CODE");

		nowTableName = "tb_student2";
		sysTablesMapDTO.setTableName(nowTableName);
		long tableCount = sysTablesMapDao.getXxxTableCount(sysTablesMapDTO);
		if (tableCount > 2) {

		}
	}
}
