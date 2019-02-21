package com.berritus.mis.bean.security;

import com.berritus.mis.core.bean.MisBean;

import java.util.Date;

public class TbSysUser extends MisBean {
    private static final long serialVersionUID = -4630209730482705728L;
    private Integer uid;

    private String userName;

    private String password;

    private Integer state;

    private Date crtDate;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }
}