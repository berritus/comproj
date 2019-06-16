package com.berritus.mis.service.base;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.dao.SysServiceConfigMapper;
import com.berritus.mis.dubbo.api.IFlowService;
import com.berritus.mis.dubbo.api.IMsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 20:12
 */
@Component
public class MsgSendBaseService extends BaseServiceEngine {
	private static final Logger logger = LoggerFactory.getLogger(MsgSendBaseService.class);


	public void sendSms(SysServiceConfig sysServiceConfig, String msg) {
		IMsgSendService taskComponent = getServiceComponent(sysServiceConfig);

		taskComponent.sendSms(msg);
	}


	public void sendEmail(SysServiceConfig sysServiceConfig, String msg) {
		IMsgSendService taskComponent = getServiceComponent(sysServiceConfig);

		taskComponent.sendEmail(msg);
	}

	public void method3(SysServiceConfig sysServiceConfig, String msg) {
		IFlowService taskComponent = getServiceComponent(sysServiceConfig);

		taskComponent.method3();
	}
}
