package com.berritus.mis.dao.security;

import com.berritus.mis.bean.security.TbSysRole;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbSysRoleMapper extends MisDao<TbSysRole, Integer> {
    List<TbSysRole> getUserRoles(String userName);

    List<TbSysRole> getResourceRoles(Integer resId);
}