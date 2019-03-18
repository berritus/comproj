/**
 * 
 */
package com.berritus.mis.task.service;

import com.berritus.mis.core.task.MisTaskComponent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Qin Guihe
 *
 */
public class DeleteFileTask extends MisTaskComponent<Long> {
	@Override
	public List<Long> getTask() {
		List<Long> list = new ArrayList<Long>();
		list.add(1000L);// 随便填，用于驱动dotask执行而已

		return list;
	}

	@Override
	public void doTask(Long task) {
//		String tempPath = SysConfig.getProperty(SysConfigConstants.FTP_TEMP_PATH);
//		if(StringHelper.isBlank(tempPath)){
//			return;
//		}return
		
		File file = new File("");
		File[] filearray = file.listFiles();
		if (filearray != null && filearray.length > 0) {
			for (File f : filearray) {
				if (f.isDirectory()) {
					//目录删除
				} else {
					//f.delete();
				}
			}
		}
	}
}
