package com.berritus.mis.controller.filter;

import com.berritus.mis.core.controller.filter.SessionCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * 这个配置类用于注册过滤器
 * Spring Boot提供@WebFilter来注册
 */
//@Configuration
public class BlockFilterConfig {

    //@Bean
    public FilterRegistrationBean sessionCheckFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SessionCheckFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("paramName", "paramValue");
        registrationBean.setName("sessionCheckFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
