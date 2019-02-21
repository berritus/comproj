package com.berritus.mis.controller.conf;

import com.berritus.mis.controller.interceptor.ProcessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class BlockMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ProcessInterceptor processInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(processInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/error");
        super.addInterceptors(registry);
    }
}
