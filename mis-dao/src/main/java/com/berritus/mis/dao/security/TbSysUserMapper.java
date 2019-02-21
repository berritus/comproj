package com.berritus.mis.dao.security;

import com.berritus.mis.bean.security.TbSysUser;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbSysUserMapper extends MisDao<TbSysUser, Integer> {
    TbSysUser selectByUserName(String userName);
}