package com.berritus.mis.service.base;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.dao.SysServiceConfigMapper;
import com.berritus.mis.dubbo.api.IDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 20:12
 */
@Component
public class MsgSendBaseService implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(MsgSendBaseService.class);

	private ApplicationContext applicationContext;

	@Autowired
	private SysServiceConfigMapper sysServiceConfigMapper;


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


	public void sender(SysServiceConfig sysServiceConfig) {
		List<SysServiceConfig> list = sysServiceConfigMapper.selectBySysServiceConfig(sysServiceConfig);

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		SysServiceConfig bean = list.get(0);

		IDemoService taskComponent = (IDemoService) applicationContext.getBean(bean.getComponentName());

		taskComponent.method1();
	}

}
