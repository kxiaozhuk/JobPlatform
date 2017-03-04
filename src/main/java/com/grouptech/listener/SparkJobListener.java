package com.grouptech.listener;

import com.grouptech.entity.JobDepender;
import com.grouptech.service.DependerService;
import com.grouptech.service.JobService;
import com.grouptech.util.Constant;
import com.grouptech.util.SpringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mahone on 2016/12/28.
 */
@Component
public class SparkJobListener implements JobListener{

    @Override
    public String getName() {
        return "SparkJobListener";
    }

    /**
     * (1)
     * 任务执行之前执行
     * Called by the Scheduler when a JobDetail is about to be executed (an associated Trigger has occurred).
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        // JobListener属于非spring管理类，用到spring的bean需另外加载Bean类
        JobService jobService = (JobService) SpringUtils.getObject("JobService");
        DependerService depService = (DependerService) SpringUtils.getObject("DependerService");

        boolean done = true;
        while(done){
            List<JobDepender> jobDepList = depService.getDependerList(jobName);
            // 作业无依赖
            if (jobDepList.size() == 0) {
                done = false; // 设置轮询退出
                Constant.logger.info("[SparkJobListener] Job-"+ jobName + " has no depender.");
            }
            // 作业存在依赖
            else {
                for(JobDepender jobDep: jobDepList){
                    // 作业依赖未完成
                    if (jobService.getJobInfo(jobDep.getDepJid()).getJobSts() != Constant.JOB_DONE_STATUS ){
                        done = true; // 设置轮询继续
                        Constant.logger.warn("[SparkJobListener] Depend job-"+ jobDep.getDepJid() + " doesn't finish yet.");
                        break; // 只要List中出现一次即可，跳出for List循环
                    }
                }
            }
        }
        Constant.logger.info("[SparkJobListener] Job:-" + jobName + "start execute.");
    }

    /**
     * (2)
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * Called by the Scheduler when a JobDetail was about to be executed (an associated Trigger has occurred),
     * but a TriggerListener vetoed it's execution.
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        Constant.logger.info("[SparkJobListener] JobExecutionVetoed()");
    }

    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * Called by the Scheduler after a JobDetail has been executed, and be for the associated Trigger's triggered(xx) method has been called.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        // JobListener属于非spring管理类，用到spring的bean需另外加载Bean类
        JobService jobService = (JobService) SpringUtils.getObject("JobService");
        if (jobException == null ){
            // 执行无出错
            Constant.logger.info("[SparkJobListener] Update job status after job was executed.");
            jobService.updateJobEndSts(jobName,Constant.JOB_DONE_STATUS);
        }
        else {
            // 执行出错
            Constant.logger.error("[SparkJobListener] Something went wrong in job running: "+jobName,jobException);
            jobService.updateJobEndSts(jobName,Constant.JOB_FAIL_STATUS);
        }
    }
}
