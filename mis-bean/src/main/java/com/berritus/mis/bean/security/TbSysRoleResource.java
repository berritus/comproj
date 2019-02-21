package com.berritus.mis.bean.security;

import com.berritus.mis.core.bean.MisBean;

import java.util.Date;

public class TbSysRoleResource extends MisBean {
    private static final long serialVersionUID = -5085349928242355789L;
    private Integer id;

    private Integer rid;

    private Integer resId;

    private Date crtDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }
}