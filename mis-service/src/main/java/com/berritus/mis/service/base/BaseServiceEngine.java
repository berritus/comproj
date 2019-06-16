package com.berritus.mis.service.base;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.dao.SysServiceConfigMapper;
import com.berritus.mis.dubbo.api.IFlowService;
import com.berritus.mis.dubbo.api.IMsgSendService;
import com.berritus.mis.dubbo.api.IServiceEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 服务基本引擎，用于协调服务调用
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/16
 */
public abstract class BaseServiceEngine implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceEngine.class);

    protected ApplicationContext applicationContext;

    protected SysServiceConfigMapper sysServiceConfigMapper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        sysServiceConfigMapper = applicationContext.getBean(SysServiceConfigMapper.class);
    }

    /**
     * @Description:
     * @Author: Qin Guihe
     * @Create: 2019/6/16
     * @return:
     */
    public <T> T getServiceComponent(SysServiceConfig sysServiceConfig) {
        T serviceComponent = null;
        try {
            sysServiceConfig.setState(0); // 取有效的服务接口
            List<SysServiceConfig> list = sysServiceConfigMapper.selectBySysServiceConfig(sysServiceConfig);

            if (CollectionUtils.isEmpty(list)) {
                // 没有配置，则根据接口获取组件
                Class clazz = BaseServiceEngine.class.getClassLoader().loadClass(sysServiceConfig.getServiceInterface());
                serviceComponent = (T) applicationContext.getBean(clazz);
                return serviceComponent;
            }

            // 默认取第一个组件
            SysServiceConfig bean = list.get(0);

            serviceComponent = (T) applicationContext.getBean(bean.getComponentName());
        } catch (Exception e) {
            logger.error("BaseServiceEngine->getServiceComponent 异常：{}", e);
        }

        return serviceComponent;
    }
}
