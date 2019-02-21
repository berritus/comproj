package com.berritus.mis.bean.security;

import com.berritus.mis.core.bean.MisBean;

import java.util.Date;

public class TbSysRole extends MisBean {
    private static final long serialVersionUID = -3854575553559214420L;
    private Integer rid;

    private String roleName;

    private String roleDesc;

    private Date crtDate;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }
}