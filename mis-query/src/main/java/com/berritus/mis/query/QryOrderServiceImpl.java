package com.berritus.mis.query;

import com.alibaba.dubbo.config.annotation.Service;
import com.berritus.mis.bean.mybatis.MisProdDef;
import com.berritus.mis.dao.MisProdDefMapper;
import com.berritus.mis.dubbo.api.QryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = QryOrderService.class)
public class QryOrderServiceImpl implements QryOrderService {
    @Autowired
    private MisProdDefMapper misProdDefMapper;

    @Override
    public MisProdDef qryMisProdDef(Integer prodId) {
        return misProdDefMapper.selectByPrimaryKey(prodId);
    }
}
