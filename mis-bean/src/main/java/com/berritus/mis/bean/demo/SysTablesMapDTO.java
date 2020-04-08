package com.berritus.mis.bean.demo;

import com.berritus.mis.core.bean.MisBean;

import java.io.Serializable;
import java.util.Date;
/**
  * @Description:
  * @Date: 2020/3/26
  * @Author: Qin Guihe
  */
public class SysTablesMapDTO extends MisBean {
    private static final long serialVersionUID = -1892808777795060288L;
    private Long seqId;

    private String tableType;

    private String mapTableName;

    private Integer tableNum;

    private Long startIndex;

    private Long endIndex;

    private Byte state;

    private Date stateDate;

    private Date crtDate;

    private String applicationCode;

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    public String getMapTableName() {
        return mapTableName;
    }

    public void setMapTableName(String mapTableName) {
        this.mapTableName = mapTableName == null ? null : mapTableName.trim();
    }

    public Integer getTableNum() {
        return tableNum;
    }

    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    public Long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

    @Override
    public String toString() {
        return "SysTablesMapDTO{" +
                "seqId=" + seqId +
                ", tableType='" + tableType + '\'' +
                ", mapTableName='" + mapTableName + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", state=" + state +
                ", stateDate=" + stateDate +
                ", crtDate=" + crtDate +
                ", applicationCode='" + applicationCode + '\'' +
                '}';
    }
}