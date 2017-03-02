package com.springboot.entity;

import java.sql.Timestamp;

/**
 * @author zhuwenhong on 2016/12/26.
 */
public class JobDepender {
    private String jobId = null;
    private String depJid = null;
    private String creNm = null;
    private Timestamp creDt = null;
    private int depSts = 0;
    private int skipInd = 0;
    private String lstModNm = null;
    private Timestamp lstModDt = null;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDepJid() {
        return depJid;
    }

    public void setDepJid(String depJid) {
        this.depJid = depJid;
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

    public int getDepSts() {
        return depSts;
    }

    public void setDepSts(int depSts) {
        this.depSts = depSts;
    }

    public int getSkipInd() {
        return skipInd;
    }

    public void setSkipInd(int skipInd) {
        this.skipInd = skipInd;
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
