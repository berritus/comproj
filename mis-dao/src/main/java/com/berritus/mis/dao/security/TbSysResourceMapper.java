package com.berritus.mis.dao.security;

import com.berritus.mis.bean.security.TbSysResource;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbSysResourceMapper extends MisDao<TbSysResource, Integer> {
    TbSysResource selectByUrl(String url);
}