package com.berritus.mis.dubbo.api;

import com.berritus.mis.bean.mybatis.MisProdDef;

public interface QryOrderService {
    public MisProdDef qryMisProdDef(Integer prodId);
}
