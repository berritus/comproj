package com.berritus.mis.controller.conf;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.berritus.mis.core.cache.annotation.EnableMisCache;
//import com.berritus.mis.core.controller.annotation.EnableMisController;
import com.berritus.mis.core.task.annotation.EnableMisTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan({"com.berritus.mis.dao"})
@ComponentScan({"com.berritus.mis.controller", "com.berritus.mis.dao",
        "com.berritus.mis.service", "com.berritus.mis.query", "com.berritus.mis.dubbo",
        "com.berritus.mis.task"})
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan({"com.berritus.mis.controller.conf"})
@EnableDubboConfiguration
@EnableAspectJAutoProxy
@EnableMisTask
@EnableMisCache
//@EnableMisController
public class MisApplication {
    public static void main(String[] args){
        SpringApplication.run(MisApplication.class, args);
    }
}
