package com.grouptech.entity;

import java.sql.Timestamp;

/**
 * @author mahone on 2016/12/26.
 */
public class JobInfo {
    private String jobId = null;
    private String jobNm = null;
    private String creNm = null;
    private Timestamp creDt = null;
    private String jobTyp = null;
    private String jobVer = null;
    private String jarPath = null;
    private String sparkMode = null;
    private String mainClass = null;
    private String appArgs = null;
    private Timestamp begnTm = null;
    private Timestamp endTm = null;
    private int jobSts = 0;
    private String trgId = null;
    private String lstModNm = null;
    private Timestamp lstModDt = null;
    private String logPath = null;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobNm() {
        return jobNm;
    }

    public void setJobNm(String jobNm) {
        this.jobNm = jobNm;
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

    public String getJobTyp() {
        return jobTyp;
    }

    public void setJobTyp(String jobTyp) {
        this.jobTyp = jobTyp;
    }

    public String getJobVer() {
        return jobVer;
    }

    public void setJobVer(String jobVer) {
        this.jobVer = jobVer;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getSparkMode() {
        return sparkMode;
    }

    public void setSparkMode(String sparkMode) {
        this.sparkMode = sparkMode;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getAppArgs() {
        return appArgs;
    }

    public void setAppArgs(String appArgs) {
        this.appArgs = appArgs;
    }

    public Timestamp getBegnTm() {
        return begnTm;
    }

    public void setBegnTm(Timestamp begnTm) {
        this.begnTm = begnTm;
    }

    public Timestamp getEndTm() {
        return endTm;
    }

    public void setEndTm(Timestamp endTm) {
        this.endTm = endTm;
    }

    public int getJobSts() {
        return jobSts;
    }

    public void setJobSts(int jobSts) {
        this.jobSts = jobSts;
    }

    public String getTrgId() {
        return trgId;
    }

    public void setTrgId(String trgId) {
        this.trgId = trgId;
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

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
