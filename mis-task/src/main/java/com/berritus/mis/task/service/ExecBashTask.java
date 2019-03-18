/**
 * 
 */
package com.berritus.mis.task.service;

import java.util.ArrayList;
import java.util.List;

import com.berritus.mis.core.task.MisTaskComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Qin Guihe
 *
 */
public class ExecBashTask extends MisTaskComponent<String> {
	private Logger logger = LoggerFactory.getLogger(ExecBashTask.class);
	
//	private ITaskService taskService;
//	public PcParamDao pcParamDao;
//
//	public PcParamDao getPcParamDao() {
//		return pcParamDao;
//	}
//
//	public void setPcParamDao(PcParamDao pcParamDao) {
//		this.pcParamDao = pcParamDao;
//	}
//
//	public ITaskService getTaskService() {
//		return taskService;
//	}
//
//	public void setTaskService(ITaskService taskService) {
//		this.taskService = taskService;
//	}
	
	@Override
	public List<String> getTask() {
		logger.warn("ExecBashTask begin");
		
//		PcParam bean = new PcParam();
//		bean.setParamCode("EXE_BASH_PATH");
//		List<PcParam> plist = pcParamDao.qryPcParamList(bean);
//		String value = "";
//		if(plist != null && plist.size() > 0){
//			value = plist.get(0).getParamValue();
//		}
//
//		List<String> list = new ArrayList<String>();
//		String str[] = value.split(",");
//		for(String str0 : str){
//			if(StringHelper.isBlank(str0)){
//				continue;
//			}
//
//			list.add(str0);
//		}
		
		return null;
	}

	@Override
	public void doTask(String task) {
		try{
			String getX="chmod a+x " + task;
            // 给予执行权限
            Process process = Runtime.getRuntime().exec(getX);
            process.waitFor();
            //执行脚本
            process = Runtime.getRuntime().exec("bash "+ task);
            process.waitFor();
		}catch(Exception e){
			logger.error("ExecBashTask error " + e.getMessage());
		}
	}
}
