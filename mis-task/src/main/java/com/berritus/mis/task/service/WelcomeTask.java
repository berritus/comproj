package com.berritus.mis.task.service;

import com.berritus.mis.core.task.MisTaskComponent;
import com.berritus.mis.core.task.service.QryTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务方式2
 */
@Component("task.WelcomeTask")
public class WelcomeTask extends MisTaskComponent<String> {
	private Logger logger = LoggerFactory.getLogger(WelcomeTask.class);
//	@Autowired
//	private QryTaskService qryTaskService;

	@Override
	public List<String> getTask(){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < 10; i++){
			list.add("hello world " + new Date().getTime());
		}
		
		return list;
	}
	
	@Override
	public void doTask(String task){
		//List<YfyPcTask> list = taskService.selectAllTask("S0X");
		//logger.warn(task.toString());
		System.out.println(task);
	}
}
