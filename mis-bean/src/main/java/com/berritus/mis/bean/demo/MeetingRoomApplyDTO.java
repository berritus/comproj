package com.berritus.mis.bean.demo;

import com.berritus.mis.core.bean.MisBean;

import java.io.Serializable;
import java.util.Date;

public class MeetingRoomApplyDTO extends MisBean {
    private static final long serialVersionUID = 6118104185892920867L;
    private Long seqId;

    private String applyId;

    private String roomId;

    private String creator;

    private Date startDate;

    private Date endDate;

    private String applicationCode;

    private Date crtDate;

    private Byte state;

    private Date modifyDate;

    private String checker;

    private Date checkDate;

    private String reason;

    private String checkerApplicationCode;

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getCheckerApplicationCode() {
        return checkerApplicationCode;
    }

    public void setCheckerApplicationCode(String checkerApplicationCode) {
        this.checkerApplicationCode = checkerApplicationCode == null ? null : checkerApplicationCode.trim();
    }

    @Override
    public String toString() {
        return "MeetingRoomApplyDTO{" +
                "seqId=" + seqId +
                ", applyId='" + applyId + '\'' +
                ", roomId=" + roomId +
                ", creator='" + creator + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", applicationCode='" + applicationCode + '\'' +
                ", crtDate=" + crtDate +
                ", state=" + state +
                ", modifyDate=" + modifyDate +
                ", checker='" + checker + '\'' +
                ", checkDate=" + checkDate +
                ", reason='" + reason + '\'' +
                ", checkerApplicationCode='" + checkerApplicationCode + '\'' +
                '}';
    }
}