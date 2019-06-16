package com.berritus.mis.common;

import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.dubbo.api.IMsgSendService;
import com.berritus.mis.service.base.BaseServiceEngine;
import com.berritus.mis.service.base.MsgSendBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-16 11:23
 * @RunWith(SpringRunner.class)
 * @SpringBootTest(classes = MisApplication.class)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class ServiceEngineTest {
	@Autowired
	private BaseServiceEngine baseServiceEngine;

	@Autowired
	private MsgSendBaseService msgSendBaseService;

	@Test
	public void test1() {
		SysServiceConfig sysServiceConfig = new SysServiceConfig();
		sysServiceConfig.setEngineType("msgSender");
		sysServiceConfig.setServiceInterface("IMsgSendService");
		sysServiceConfig.setApplicationCode("mis-521");
		msgSendBaseService.sendEmail(sysServiceConfig, "hello");
		System.out.println("success");
	}

	@Test
	public void test2() {
		SysServiceConfig sysServiceConfig = new SysServiceConfig();
		sysServiceConfig.setEngineType("msgSender");
		sysServiceConfig.setServiceInterface("com.berritus.mis.dubbo.api.IFlowService");
		sysServiceConfig.setApplicationCode("mis-521");
		msgSendBaseService.method3(sysServiceConfig, "hello");
		System.out.println("success");
	}
}
