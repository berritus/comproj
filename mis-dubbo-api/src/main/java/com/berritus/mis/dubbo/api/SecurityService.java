package com.berritus.mis.dubbo.api;


import com.berritus.mis.bean.security.TbSysRole;
import com.berritus.mis.bean.security.TbSysUser;

import java.util.List;

public interface SecurityService {
    TbSysUser selectByUserName(String userName);
    List<TbSysRole> getUserRoles(String userName);
}
