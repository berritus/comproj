package com.berritus.mis.service.impl;

import com.berritus.mis.bean.mybatis.MisOrder;
import com.berritus.mis.bean.mybatis.MisProdDef;
import com.berritus.mis.core.cache.lock.IRedisLock;
import com.berritus.mis.core.cache.redis.IRedisService;
import com.berritus.mis.dao.MisOrderMapper;
import com.berritus.mis.dao.MisProdDefMapper;
import com.berritus.mis.dubbo.api.OrderService;
import com.berritus.mis.dubbo.api.QryOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    @Autowired
    private MisOrderMapper misOrderMapper;
    @Autowired
    private MisProdDefMapper misProdDefMapper;
    @Autowired
    private QryOrderService qryOrderService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IRedisLock redisLock;

    @Override
    public String genOrderCode(int prodId) {
        String orderCode = sdf.format(new Date());
        int index = atomicInteger.incrementAndGet();
        orderCode += "-" + prodId + "-" + index;
        return orderCode;
    }

    @Override
    public Date formatDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = "20190219004524304";
        Date date = sdf.parse(str);
        return date;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long insertMisOrder(MisOrder record) {
        for(int i = 0; i <5; i++){
            MisProdDef prodDef = misProdDefMapper.selectByPrimaryKey(record.getProdId());
            if(prodDef.getInStock() == 0){
                return 0;
            }

            MisProdDef bean = new MisProdDef();
            bean.setProdId(record.getProdId());
            bean.setInStock(prodDef.getInStock() - 1);
            bean.setVersion(prodDef.getVersion());
            int ret = updateMisProdDef(bean);
            if(ret == 0){
                logger.warn(Thread.currentThread().getName() + "continue to submit order");
                continue;
            }

            String orderCode = genOrderCode(record.getProdId());
            record.setOrderCode(orderCode);
            record.setCrtDate(new Date());
            record.setState((byte)0);
            record.setStateDate(new Date());
            record.setCount(1);
            misOrderMapper.insert(record);
            return record.getOrderId();
        }

        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int updateMisProdDef(MisProdDef record) {
        return misProdDefMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional
    public int insertMisProdDef(MisProdDef record) {
        record.setCrtDate(new Date());
        misProdDefMapper.insert(record);
        return record.getProdId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long insertMisOrder2(MisOrder record) {
        MisProdDef prodDef = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(record.getProdId());
        String key = stringBuilder.toString();

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(System.currentTimeMillis() + 3000);
        String value = stringBuilder2.toString();

        boolean lock = redisLock.lock(record.getProdId() + "", value);
        if(lock){
            prodDef = misProdDefMapper.selectByPrimaryKey(record.getProdId());
            if(prodDef == null){
                logger.error("prodDef is null");
                return 0;
            }

            if(prodDef.getInStock() == 0){
                return 0;
            }

            String orderCode = genOrderCode(record.getProdId());
            record.setOrderCode(orderCode);
            record.setCrtDate(new Date());
            record.setState((byte)0);
            record.setStateDate(new Date());
            record.setCount(1);
            misOrderMapper.insert(record);

            MisProdDef bean = new MisProdDef();
            bean.setProdId(record.getProdId());
            bean.setInStock(prodDef.getInStock() - 1);
            updateMisProdDef(bean);

            redisLock.unlock(key, value);
            return record.getOrderId();
        }

        return 0;
    }
}
