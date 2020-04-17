package com.berritus.mis.controller.conf;

import com.berritus.mis.dao.interceptor.SQLInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-04-17
 */
@Configuration
public class MybatisConfiguration {
	@Bean
	public SQLInterceptor mybatisInterceptor() {
		SQLInterceptor interceptor = new SQLInterceptor();
		Properties properties = new Properties();
		// 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
		interceptor.setProperties(properties);
		return interceptor;
	}
}
