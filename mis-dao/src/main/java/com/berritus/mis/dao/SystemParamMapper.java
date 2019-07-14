package com.berritus.mis.dao;

import com.berritus.mis.bean.sys.SystemParam;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemParamMapper extends MisDao<SystemParam, String> {
    List<SystemParam> listSystemParams(SystemParam systemParam);
}