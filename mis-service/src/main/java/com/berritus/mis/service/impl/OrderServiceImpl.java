package com.berritus.mis.service.impl;

import com.berritus.mis.bean.mybatis.MisOrder;
import com.berritus.mis.bean.mybatis.MisProdDef;
import com.berritus.mis.dao.MisOrderMapper;
import com.berritus.mis.dao.MisProdDefMapper;
import com.berritus.mis.dubbo.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    @Autowired
    private MisOrderMapper misOrderMapper;
    @Autowired
    private MisProdDefMapper misProdDefMapper;

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
        MisProdDef prodDef = null;
        synchronized(this){
            prodDef = misProdDefMapper.selectByPrimaryKey(record.getProdId());
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
        }

        return record.getOrderId();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int updateMisProdDef(MisProdDef record) {
        misProdDefMapper.updateByPrimaryKeySelective(record);
        return record.getProdId();
    }

    @Override
    @Transactional
    public int insertMisProdDef(MisProdDef record) {
        record.setCrtDate(new Date());
        misProdDefMapper.insert(record);
        return record.getProdId();
    }

}
