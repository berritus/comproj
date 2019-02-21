package com.berritus.mis.dubbo.api;

import com.berritus.mis.bean.mybatis.MisOrder;
import com.berritus.mis.bean.mybatis.MisProdDef;
import com.berritus.mis.bean.mybatis.SysFiles;

import java.text.ParseException;
import java.util.Date;

public interface OrderService {
    public String genOrderCode(int prodId);

    public Date formatDate() throws ParseException;

    long insertMisOrder(MisOrder record);

    int updateMisProdDef(MisProdDef record);

    int insertMisProdDef(MisProdDef record);
}
