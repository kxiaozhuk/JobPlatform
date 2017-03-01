package com.touchstone.dao;

import com.touchstone.entity.JobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@Mapper
public interface JobDao {
    public int countAllJob();
    public JobInfo selectJobById(@Param("jobId")String jobId);
    public int insertJob(@Param("jobInfo")JobInfo jobInfo);
    public int deleteJobById(@Param("jobId")String jobId);
    public List<JobInfo> selectAllJob();
    public int updateJob(@Param("jobInfo")JobInfo jobInfo);
    public int updateJobBegnSts(@Param("jobId")String jobId,@Param("jobSts")int jobSts);
    public int updateJobEndSts(@Param("jobId")String jobId,@Param("jobSts")int jobSts);
    public List<JobInfo> selectJobByIf(@Param("jobInfo")JobInfo jobInfo);
    public List<Map<String,String>> groupByStatus();

}
