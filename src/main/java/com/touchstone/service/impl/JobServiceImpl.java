package com.touchstone.service.impl;

import com.touchstone.dao.JobDao;
import com.touchstone.entity.JobInfo;
import com.touchstone.entity.SparkJob;
import com.touchstone.service.JobService;
import com.touchstone.service.TriggerService;
import com.touchstone.util.Constant;
import com.touchstone.util.SchedulerUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.quartz.JobBuilder.newJob;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@Service("JobService")
public class JobServiceImpl implements JobService {
    //private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private JobDao jDao;
    @Autowired
    private TriggerService triggerService;

    /**
     * 查找id JobInfo
     * @param jobId
     * @return
     */
    @Override
    public JobInfo getJobInfo(String jobId) {
        JobInfo job = jDao.selectJobById(jobId);
        if(job == null){
            Constant.logger.error("[JobService] No such job by id: "+ jobId);
        }
        return job;
    }

    /**
     * 返回所有Job
     * @return
     */
    @Override
    public List<JobInfo> getJobList() {
        List<JobInfo> jobs = jDao.selectAllJob();
        if(jobs.size() == 0){
            Constant.logger.error("[JobService] Job list is empty.");
        }
        return jobs;
    }

    /**
     * 增加作业
     * @param jobInfo
     * @throws IOException
     */
    @Override
    public int createJob(JobInfo jobInfo){
        if ( jobInfo.getJobId() == null || jobInfo.getJobId() == "" ){
            jobInfo.setJobId(UUID.randomUUID().toString().replace("-",""));
            jobInfo.setJobTyp(Constant.JOB_GROUP_NAME);
        }
        boolean flag = initialJob(jobInfo);
        if ( !flag ) {
            return 0;
        }
        int count = jDao.insertJob(jobInfo);
        if(count >= 1){
            Constant.logger.info("[JobService] Create job success.");
        }else {
            Constant.logger.error("[JobService] Create job failed.");
        }
        return count;
    }

    /**
     * 初始化作业
     * @param jobInfo
     */
    @Override
    public boolean initialJob(JobInfo jobInfo) {
        // cachedThreadPool.execute( () -> {
        try {
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            JobDataMap jdMap = new JobDataMap();
            jdMap.put("spark_mode",jobInfo.getSparkMode());
            jdMap.put("jar_path",jobInfo.getJarPath());
            jdMap.put("main_class",jobInfo.getMainClass());
            jdMap.put("app_args",jobInfo.getAppArgs());
            // 任务
            JobDetail jobDetail = newJob(SparkJob.class)
                    .withIdentity(jobInfo.getJobId(),Constant.JOB_GROUP_NAME) // 任务执行类,任务名,任务组
                    .usingJobData(jdMap)
                    .storeDurably() // 设置无触发器时依旧可用
                    .build();

            if (!jobInfo.getTrgId().isEmpty()){
                Trigger trigger = triggerService.getTrigger(jobInfo.getJobId(),jobInfo.getTrgId());
                // 往调度管理器添加任务和触发器
                sched.scheduleJob(jobDetail, trigger);
            } else {
                // 可以立即执行. 无trigger注册
                sched.addJob(jobDetail,true);
            }
            //res = true;
        } catch (Exception e) {
            Constant.logger.error("[JobService] Initial job failed.",e);
            throw new RuntimeException(e);
        }
        //});
        return true;
    }

    /**
     * 更新作业
     * @param jobInfo
     * @return
     */
    @Override
    public int updateJob(JobInfo jobInfo) {
        int count;
        Boolean trgChange = jobInfo.getTrgId() == getJobInfo(jobInfo.getJobId()).getTrgId() ? false : true;
        try {
            // 先更新Jobhe和Trigger
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            // 修改JobDetail
            JobKey oldJkey = new JobKey(jobInfo.getJobId(),Constant.JOB_GROUP_NAME);
            JobDetail oldJDetail = sched.getJobDetail(oldJkey);
            JobBuilder jb = oldJDetail.getJobBuilder();
            // JobDataMap是否改变
            JobDataMap jdMap = new JobDataMap();
            jdMap.put("spark_mode",jobInfo.getSparkMode());
            jdMap.put("jar_path",jobInfo.getJarPath());
            jdMap.put("main_class",jobInfo.getMainClass());
            jdMap.put("app_args",jobInfo.getAppArgs());
            if ( !jdMap.equals(oldJDetail.getJobDataMap()) ){
                jb = jb.setJobData(jdMap);
            }
            // store, and set overwrite flag to 'true'
            sched.addJob(jb.build(),true);
            if ( trgChange ){
                // Schedule the trigger
                sched.scheduleJob(triggerService.getTrigger(jobInfo.getJobId(),jobInfo.getTrgId()));
            }
            // 再更新数据库记录
            count = jDao.updateJob(jobInfo);
            if(count >= 1){
                Constant.logger.info("[JobService] Update job success.");
            }else {
                Constant.logger.error("[JobService] Update job failed because of record is empty in database.");
            }
        } catch (Exception e) {
            Constant.logger.error("[JobService] Update job failed.",e);
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * 删除作业
     * @param jobId
     */
    @Override
    public int deleteJob(String jobId) {
        int count;
        try {
            // 先移除Quartz中的任务
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            String trgId = getJobInfo(jobId).getTrgId();
            if ( !trgId.isEmpty()){
                TriggerKey Tkey = new TriggerKey(jobId,trgId);
                sched.pauseTrigger(Tkey); // 停止触发器
                sched.unscheduleJob(Tkey); // 移除触发器
            }
            sched.deleteJob(new JobKey(jobId,Constant.JOB_GROUP_NAME));
            // 再删除数据库记录
            count = jDao.deleteJobById(jobId);
            if(count >= 1){
                Constant.logger.info("[JobService] Delete job success.");
            }else {
                Constant.logger.error("[JobService] Delete job failed because of record is empty in database.");
            }
        } catch (Exception e) {
            Constant.logger.error("[JobService] Delete job failed.",e);
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * 运行作业
     * @param jobId
     */
    @Override
    public boolean runJob(String jobId){
        boolean res = false;
        try {
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
             // 启动
            if (sched.isShutdown() || !sched.isStarted()){
                sched.start();
            }
            // 运行任务
            jDao.updateJobBegnSts(jobId,Constant.JOB_RUN_STATUS);
            sched.triggerJob(new JobKey(jobId,Constant.JOB_GROUP_NAME));
            Constant.logger.info("[JobService] Job start run success.");
            res = true;
        } catch (Exception e) {
            jDao.updateJobEndSts(jobId,Constant.JOB_FAIL_STATUS);
            Constant.logger.error("[JobService] Job start run failed.",e);
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * 暂停作业
     * @param jobId
     */
    @Override
    public boolean stopJob(String jobId){
        boolean res = false;
        try {
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            JobKey key = new JobKey(jobId,Constant.JOB_GROUP_NAME);
            // 暂停任务
            sched.pauseJob(key);
            jDao.updateJobEndSts(jobId,Constant.JOB_STOP_STATUS); // 已终止
            Constant.logger.info("[JobService] Job stop success.");
            res = true;
        } catch (Exception e) {
            Constant.logger.error("[JobService] Job stop failed.",e);
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public int updateJobEndSts(String jobId, int jobSts){
        return jDao.updateJobEndSts(jobId,jobSts);
    }

    @Override
    public Map<String,String> groupByStatus(){
        List<Map<String,String>> listMap = jDao.groupByStatus();
        Map<String,String> map = new HashMap<>();
        listMap.forEach(e->{
            map.put(e.get("job_sts"),e.get("sts_cnt"));
        });
        return map;
    }
}
