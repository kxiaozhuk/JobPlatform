package com.touchstone.dao;

import com.touchstone.entity.JobDepender;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@Mapper
public interface DependerDao {
    public List<JobDepender> selectDependerByJob(@Param("jobId") String jobId);
    public List<JobDepender> selectJobByDepender(@Param("depJid") String depJid);
    public int insertDepender(@Param("jobDep") JobDepender jobDep);
    public int deleteByJob(@Param("jobId") String jobId);
    public int deleteByDepender(@Param("depJid") String depJid);
    public List<JobDepender> selectAllDepender();
    public int updateDepender(@Param("jobDep") JobDepender jobDep);
}
