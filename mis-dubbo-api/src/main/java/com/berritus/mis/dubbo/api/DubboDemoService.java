package com.berritus.mis.dubbo.api;


import com.berritus.mis.bean.mybatis.MisProdDef;
import com.berritus.mis.bean.school.TbStudent;

/**
 * 注意接口包名要与提供服务端的包名一致，接口名也要与提供服务端的接口名一致
 * 比如服务端有个接口com.berritus.block.service.DubboDemoService
 * 则消费者端也必须要有一个com.berritus.block.service.DubboDemoService
 * 才能完成reference，不然会报null
 */
public interface DubboDemoService {
    public String helloDubbo();
    public TbStudent addStudent(TbStudent student);
}
