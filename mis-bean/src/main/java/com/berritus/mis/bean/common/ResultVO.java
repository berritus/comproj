package com.berritus.mis.bean.common;

import com.berritus.mis.core.bean.MisBean;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/7/14
 */
public class ResultVO extends MisBean {
    private static final long serialVersionUID = 1199635339118108569L;

    private Boolean successful;
    private String msg;
    private Integer rspCode;

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getRspCode() {
        return rspCode;
    }

    public void setRspCode(Integer rspCode) {
        this.rspCode = rspCode;
    }
}
