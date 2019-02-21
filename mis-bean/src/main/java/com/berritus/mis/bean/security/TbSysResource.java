package com.berritus.mis.bean.security;

import com.berritus.mis.core.bean.MisBean;

import java.util.Date;

public class TbSysResource extends MisBean {
    private static final long serialVersionUID = 3072006429852395687L;
    private Integer resId;

    private String url;

    private String urlName;

    private Date crtDate;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName == null ? null : urlName.trim();
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }
}