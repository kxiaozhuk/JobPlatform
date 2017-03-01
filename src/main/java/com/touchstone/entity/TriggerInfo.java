package com.touchstone.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author zhuwenhong on 2016/12/26.
 */
public class TriggerInfo {
    private String trgId = null;
    private String trgNm = null;
    private String creNm = null;
    private Timestamp creDt = null;
    private String runCycl = null;
    private Time runDayTm = null;
    private int runDayWeek = 0;
    private int runDayMon = 0;
    private Date begnDt = null;
    private Date endDt = null;
    private Date skipDt = null;
    private int trgSts = 0;
    private String lstModNm = null;
    private Timestamp lstModDt = null;

    public String getTrgId() {
        return trgId;
    }

    public void setTrgId(String trgId) {
        this.trgId = trgId;
    }

    public String getTrgNm() {
        return trgNm;
    }

    public void setTrgNm(String trgNm) {
        this.trgNm = trgNm;
    }

    public String getCreNm() {
        return creNm;
    }

    public void setCreNm(String creNm) {
        this.creNm = creNm;
    }

    public Timestamp getCreDt() {
        return creDt;
    }

    public void setCreDt(Timestamp creDt) {
        this.creDt = creDt;
    }

    public String getRunCycl() {
        return runCycl;
    }

    public void setRunCycl(String runCycl) {
        this.runCycl = runCycl;
    }

    public Time getRunDayTm() {
        return runDayTm;
    }

    public void setRunDayTm(Time runDayTm) {
        this.runDayTm = runDayTm;
    }

    public int getRunDayWeek() {
        return runDayWeek;
    }

    public void setRunDayWeek(int runDayWeek) {
        this.runDayWeek = runDayWeek;
    }

    public int getRunDayMon() {
        return runDayMon;
    }

    public void setRunDayMon(int runDayMon) {
        this.runDayMon = runDayMon;
    }

    public Date getBegnDt() {
        return begnDt;
    }

    public void setBegnDt(Date begnDt) {
        this.begnDt = begnDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public Date getSkipDt() {
        return skipDt;
    }

    public void setSkipDt(Date skipDt) {
        this.skipDt = skipDt;
    }

    public int getTrgSts() {
        return trgSts;
    }

    public void setTrgSts(int trgSts) {
        this.trgSts = trgSts;
    }

    public String getLstModNm() {
        return lstModNm;
    }

    public void setLstModNm(String lstModNm) {
        this.lstModNm = lstModNm;
    }

    public Timestamp getLstModDt() {
        return lstModDt;
    }

    public void setLstModDt(Timestamp lstModDt) {
        this.lstModDt = lstModDt;
    }
}
