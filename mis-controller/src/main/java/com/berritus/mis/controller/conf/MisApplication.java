package com.berritus.mis.controller.conf;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.berritus.mis.core.cache.annotation.EnableMisCache;
import com.berritus.mis.core.component.annotation.EnableCoreComponent;
import com.berritus.mis.core.controller.annotation.EnableMisController;
import com.berritus.mis.core.dynamicdb.xa.DynamicDataSourceRegister;
import com.berritus.mis.core.dynamicdb.xa.annotation.EnableCoreDynamicdbXa;
import com.berritus.mis.core.rabbitmq.annotation.EnableMisRegisterRabbitMQ;
import com.berritus.mis.core.rabbitmq.annotation.EnableMisUseRabbitMQ;
import com.berritus.mis.core.task.annotation.EnableMisTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableJpaRepositories({"com.berritus.mis.dao"})
@MapperScan({"com.berritus.mis.dao"})
@ComponentScan({"com.berritus.mis.controller", "com.berritus.mis.dao",
        "com.berritus.mis.service", "com.berritus.mis.query", "com.berritus.mis.dubbo",
        "com.berritus.mis.task", "com.berritus.test"})
@SpringBootApplication
//@EnableTransactionManagement
@ServletComponentScan({"com.berritus.mis.controller.conf"})
@EnableDubboConfiguration
@EnableAspectJAutoProxy
@EnableMisCache
@EnableMisController
@EnableAsync
//@EnableMisTask
//@EnableMisRegisterRabbitMQ
@EnableMisUseRabbitMQ
@EnableCoreComponent
@EnableCoreDynamicdbXa
@Import({DynamicDataSourceRegister.class})
public class MisApplication {
    public static void main(String[] args){
        SpringApplication.run(MisApplication.class, args);
    }
}
