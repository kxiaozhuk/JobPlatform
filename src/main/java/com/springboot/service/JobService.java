package com.springboot.service;

import com.springboot.entity.JobInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhuwenhong on 2016/12/26.
 */
public interface JobService {

    public JobInfo getJobInfo(String jobId);
    public List<JobInfo> getJobList();
    public int createJob(JobInfo jobInfo);
    public int updateJob(JobInfo jobInfo);
    public int deleteJob(String jobId);
    public boolean runJob(String jobId);
    public boolean stopJob(String jobId);
    public boolean initialJob(JobInfo jobInfo);
    public int updateJobEndSts(String jobId, int jobSts);
    public Map<String,String> groupByStatus();

}
