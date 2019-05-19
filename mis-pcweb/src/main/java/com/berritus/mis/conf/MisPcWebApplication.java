package com.berritus.mis.conf;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@ComponentScan({"com.berritus.mis"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800, redisNamespace = "tl")
@EnableDubboConfiguration
public class MisPcWebApplication {
    public static void main(String[] args){
        SpringApplication.run(MisPcWebApplication.class, args);
    }
}
