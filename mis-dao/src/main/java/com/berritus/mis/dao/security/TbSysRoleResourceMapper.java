package com.berritus.mis.dao.security;

import com.berritus.mis.bean.security.TbSysRoleResource;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbSysRoleResourceMapper extends MisDao<TbSysRoleResource, Integer> {
    List<TbSysRoleResource> getSysRoleResourceByResId(Integer resId);
}