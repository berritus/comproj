package com.berritus.mis.bean.demo;

import java.util.Date;

public class MeetingRoomApplyExt extends MeetingRoomApplyDTO {
    private String crtDateStr;
    private String modifyDateStr;
    private String startDateStr;
    private String endDateStr;

    public String getCrtDateStr() {
        return crtDateStr;
    }

    public void setCrtDateStr(String crtDateStr) {
        this.crtDateStr = crtDateStr;
    }

    public String getModifyDateStr() {
        return modifyDateStr;
    }

    public void setModifyDateStr(String modifyDateStr) {
        this.modifyDateStr = modifyDateStr;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
