package com.springboot.service;

import com.springboot.entity.JobDepender;
import java.util.List;

/**
 * @author zhuwenhong on 2016/12/26.
 */
public interface DependerService {

    public List<JobDepender> getJobList(String depJid);
    public List<JobDepender> getDependerList(String jobId);
    public List<JobDepender> getALLList();
    public int createDepender(JobDepender jobDep);
    public int updateDepender(JobDepender jobDep);
    public int deleteByJob(String jobId);
    public int deleteByDepender(String depJid);

}
