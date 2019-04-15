package com.berritus.mis.query;

import com.alibaba.dubbo.config.annotation.Service;

import com.berritus.mis.bean.security.TbSysRole;
import com.berritus.mis.bean.security.TbSysUser;
import com.berritus.mis.dao.security.TbSysRoleMapper;
import com.berritus.mis.dao.security.TbSysUserMapper;
import com.berritus.mis.dubbo.api.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = SecurityService.class)
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private TbSysUserMapper sysUserMapper;
    @Autowired
    private TbSysRoleMapper sysRoleMapper;

    @Override
    public TbSysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public List<TbSysRole> getUserRoles(String userName) {
        return sysRoleMapper.getUserRoles(userName);
    }
}
