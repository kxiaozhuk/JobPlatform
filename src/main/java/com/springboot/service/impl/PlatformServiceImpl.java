package com.springboot.service.impl;

import com.springboot.entity.JobInfo;
import com.springboot.service.JobService;
import com.springboot.service.PlatformService;
import com.springboot.util.Constant;
import com.springboot.util.SchedulerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwenhong on 2016/12/29.
 */
@Service("PlatformService")
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private JobService jobService;

    /**
     * 平台初始化，加载任务和触发器到内存中
     */
    public boolean initial(){
        boolean res = false;
        List<JobInfo> jobList = jobService.getJobList();
        int count = 0;
        for(JobInfo jobInfo : jobList ){
            try {
                jobService.initialJob(jobInfo);
                Constant.logger.info("[PlatformService] Job initial success: "+ jobInfo.getJobId());
                count++;
            } catch (Exception e){
                Constant.logger.error("[PlatformService] Job initial failed: "+ jobInfo.getJobId());
                throw new RuntimeException(e);
            }
        }
        if (count == jobList.size()){
            try {
                SchedulerUtils.startup(Constant.OWNER);
                res = true;
            } catch (Exception e){
                Constant.logger.error("[PlatformService] Scheduler start failed: "+ Constant.OWNER);
                throw new RuntimeException(e);
            }
        }
        return res;
    }

}
