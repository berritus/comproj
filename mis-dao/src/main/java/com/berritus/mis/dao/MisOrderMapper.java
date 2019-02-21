package com.berritus.mis.dao;

import com.berritus.mis.bean.mybatis.MisOrder;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface MisOrderMapper extends MisDao<MisOrder, Long> {

}