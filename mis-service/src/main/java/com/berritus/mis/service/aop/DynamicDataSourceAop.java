package com.berritus.mis.service.aop;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.berritus.mis.core.bean.dynamicdb.DataSourceInfo;
import com.berritus.mis.core.cache.redis.IRedisService;
import com.berritus.mis.core.dynamicdb.DataSourceService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @version V1.0
 * @Description: 必须在事务开始之前拦截，并切换好数据源，所以order为-1，比事务要小，就会先进入当前的拦截器
 * @author: minstone.liuyb
 * @date: 2017年8月25日 上午午10:31:26
 * @copyright 广州明动软件股份有限公司 Copyright (c) 2016
 */
@Aspect
@Component
@Order(-1)
public class DynamicDataSourceAop {
	public static Logger logger = LoggerFactory.getLogger(DynamicDataSourceAop.class);

	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private IRedisService redisService;

	public static final String DATABASE_CACHE = "DATABASE_CACHE_";
	public static final int LOGGER_MAX_LEN = 256;
	// 切点
//	@Pointcut(value = "!@annotation(com.minstone.instance.configuration.NoChangeDataSource)")
//	private void aspectNoChangeDataSource() {
//	}

	// 切点
//	@Pointcut(" execution(public * com.minstone.instance..*.service.*Service.*(..)) and args(..) ")
//	private void aspectService() {
//
//	}

	// 切点平台父类的增删改查
//	@Pointcut(value = " execution(public * com.minstone.common.service.CrudService.*(..)) and args(..)")
//	private void aspectServicePlat() {
//	}

	//专用于切本项目中WorkApiImpl类
	@Pointcut(value = " execution(public * com.berritus.mis.service.impl.MessageServiceImpl.*(..)) and args(..)")
	private void aspectServiceForApi() {
	}

//	@Pointcut(value = " execution(public * com.minstone.instance.async.component.impl.MessageReminderServiceImpl.*(..)) and args(..)")
//	private void aspectServiceSms() {
//	}

	@Before(value = "aspectServiceForApi()")
	public void doBefore(JoinPoint joinPoint) throws Exception {
		// 2019-07-18 begin Qin Guihe
		// old
//		Object[] args = joinPoint.getArgs(); // 获得参数
//		if (args != null && args.length > 0) {
//			Object object = args[args.length - 1];// 获得最后一个参数，所以数据源的参数必须放到最后
//			String dbCode = "";
//			if (object instanceof String) {
//				dbCode = object.toString();
//			} else if (object instanceof Map) {
//				Map<Object, Object> map = (Map<Object, Object>) object;
//				dbCode = (String) map.get("applicationCode");
//			} else if (object instanceof BaseEntity) {
//				BaseEntity entity = (BaseEntity) object;
//				dbCode = entity.getApplicationCode();
//			} else if (object instanceof FunctionParas) {
//				FunctionParas entity = (FunctionParas) object;
//				dbCode = entity.getSysCode();
//			}
//			if (StringUtils.isEmpty(dbCode)) {
//				log.error("数据源（租户id）：" + dbCode + " 不存在 ，对应的方法是： " + joinPoint.getSignature());
//				throw new Exception("数据源（租户id）：" + dbCode + " 不存在 ，对应的方法是： " + joinPoint.getSignature());
//			}
        logger.info("当前数据源（租户id）：");
//			//dbCode = "TEST_NOEXISTS";
//			DataSourceUtils.changeDataSource(dbCode.toUpperCase());
//		}

//		String applicationCode = "MIS_TEST_DB";
//		String cacheKey = DATABASE_CACHE + applicationCode.toUpperCase();
//		DataSourceInfo dataSourceInfo = redisService.get(cacheKey);
//		if (dataSourceInfo == null) {
//			dataSourceInfo = new DataSourceInfo();
//			dataSourceInfo.setApplicationCode(applicationCode);
//			dataSourceInfo.setDriverClassName("com.mysql.jdbc.Driver");
//			dataSourceInfo.setUrl("jdbc:mysql://localhost:3306/md_rst_platform?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
//			dataSourceInfo.setUsername("root");
//			dataSourceInfo.setPassword("lovesnow");
//			dataSourceInfo.setInitialSize("1");
//			dataSourceInfo.setMinIdle("1");
//			dataSourceInfo.setMaxWait("60000");
//			dataSourceInfo.setMaxActive("50");
//			dataSourceInfo.setMinEvictableIdleTimeMillis("60000");
//		}
//
//		dataSourceService.changeDataSource(dataSourceInfo);
//		redisService.set(cacheKey, dataSourceInfo);
	}

	@After(value = "aspectServiceForApi())")
	public void doAfter(JoinPoint joinPoint) throws Exception {
		//DataSourceUtils.restoreDataSource();
	}

	@AfterThrowing(value = "aspectServiceForApi())", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		//String baseInfo = DataSourceUtils.getLogBaseInfo(joinPoint);
		//logger.info("调用接口出现异常，基本信息={}，异常={}", baseInfo, e);
		//DataSourceUtils.restoreDataSource();
	}

	/**
	 * @Description: 获取调用基本信息
	 * @Author: Qin Guihe
	 * @Create: 2019/7/18
	 * @return:
	 */
	public String getMethodBaseInfo(JoinPoint joinPoint) {
		String info = "";
		try {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			Method method = methodSignature.getMethod();

			String className = joinPoint.getTarget().getClass().getName();
			String methodName = method.getName();
			Object args[] = joinPoint.getArgs();
			String param = "";
			for (int i = 0; i < args.length; i++) {
				Object obj = args[i];

				if (i == args.length - 1) {
					param += JSON.toJSONString(obj);
				} else {
					param += JSON.toJSONString(obj) + ">>>";
				}
			}

			info = className + "." + methodName + ",args[" + param + "]";

			if (info.length() > LOGGER_MAX_LEN) {
				info = info.substring(0, LOGGER_MAX_LEN);
			}
		} catch (Exception e) {
			logger.warn("获取调用基本信息失败，但不影响程序执行，{}", e);
		}

		return info;
	}
}