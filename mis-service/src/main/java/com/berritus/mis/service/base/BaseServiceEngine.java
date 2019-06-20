package com.berritus.mis.service.base;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.common.constant.SysConfigConstants;
import com.berritus.mis.core.cache.redis.IRedisService;
import com.berritus.mis.dao.SysServiceConfigMapper;
import com.berritus.mis.dubbo.api.IFlowService;
import com.berritus.mis.dubbo.api.IMsgSendService;
import com.berritus.mis.dubbo.api.IServiceEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 服务基本引擎，用于协调服务调用
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/16
 */
public abstract class BaseServiceEngine<T> implements ApplicationContextAware {

    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceEngine.class);

    protected ApplicationContext applicationContext;

    protected SysServiceConfigMapper sysServiceConfigMapper;

    protected IRedisService redisService;

    @Value("${mis.sys.code}")
    private String sysCode;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        sysServiceConfigMapper = applicationContext.getBean(SysServiceConfigMapper.class);
        redisService = applicationContext.getBean(IRedisService.class);
    }

    /**
     * @Description:
     * @Author: Qin Guihe
     * @Create: 2019/6/16
     * @return:
     */
    public <T> T getServiceComponent() {
        T serviceComponent = null;
        String logId = UUID.randomUUID().toString();

        try {
            SysServiceConfig sysServiceConfig = new SysServiceConfig();
            Type types = this.getClass().getGenericSuperclass();
            Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
            String interfaceName = "";
            for (Type t : genericType) {
                interfaceName = t.getTypeName();
            }

            logger.info("logId={}, 获取到接口名：{}", logId, interfaceName);
            if (StringUtils.isBlank(interfaceName)) {
                throw new Exception("未获取到接口名，请检查配置");
            }

            if (StringUtils.isBlank(sysCode)) {
                throw new Exception("系统编码为空，请检查application.properties配置");
            }

            String keys = sysCode + ":" + interfaceName;
            String componentName = redisService.get(keys); //缓存获取组件名

            if (StringUtils.isBlank(componentName)) {
                synchronized(this) {
                    // 双重检查
                    componentName = redisService.get(keys);
                    if (StringUtils.isBlank(componentName)) {
                        sysServiceConfig.setApplicationCode(sysCode);
                        sysServiceConfig.setServiceInterface(interfaceName);
                        sysServiceConfig.setState(0); // 取有效的服务接口
                        List<SysServiceConfig> list = sysServiceConfigMapper.selectBySysServiceConfig(sysServiceConfig);
                        if (CollectionUtils.isEmpty(list)) {
                            // 设置默认接口名
                            componentName = SysConfigConstants.DEFAULT_COMPONENT_NAME;
                        } else {
                            // 默认取第一个组件
                            SysServiceConfig bean = list.get(0);
                            componentName = bean.getComponentName();
                        }

                        // 置入缓存，一天后过期
                        redisService.set(keys, componentName, SysConfigConstants.MAX_CACHE_TIME);
                    }
                }
            }

            if (SysConfigConstants.DEFAULT_COMPONENT_NAME.equals(componentName)){
                // 如果取到的是默认接口名
                Class clazz = BaseServiceEngine.class.getClassLoader().loadClass(interfaceName);
                serviceComponent = (T) applicationContext.getBean(clazz);
                return serviceComponent;
            }

            serviceComponent = (T) applicationContext.getBean(componentName);

        } catch (Exception e) {
            logger.error("logId={}，获取组件异常：{}", logId, e);
        }

        return serviceComponent;
    }

    public <T> T getServiceComponentByConfig(SysServiceConfig sysServiceConfig) {
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
            logger.error("获取组件异常：{}", e);
        }

        return serviceComponent;
    }
}
