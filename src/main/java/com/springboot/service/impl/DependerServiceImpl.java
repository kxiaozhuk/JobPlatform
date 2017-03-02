package com.springboot.service.impl;

import com.springboot.dao.DependerDao;
import com.springboot.entity.JobDepender;
import com.springboot.service.DependerService;
import com.springboot.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zhuwenhong on 2016/12/30.
 */
@Service("DependerService")
public class DependerServiceImpl implements DependerService {
    @Autowired
    private DependerDao Dao;

    @Override
    public int createDepender(JobDepender jobDep) {
        int count = Dao.insertDepender(jobDep);
        if(count >= 1){
            Constant.logger.info("[DependerService] Create job depender success.");
        }else {
            Constant.logger.error("[DependerService] Create job depender failed.");
        }
        return count;
    }

    @Override
    public List<JobDepender> getJobList(String depJid) {
        List<JobDepender> res = Dao.selectJobByDepender(depJid);
        if(res.size() == 0){
            Constant.logger.error("[DependerService] Job depender is empty.");
        }
        return res;
    }

    @Override
    public List<JobDepender> getDependerList(String jobId) {
        List<JobDepender> res = Dao.selectDependerByJob(jobId);
        if(res.size() == 0){
            Constant.logger.error("[DependerService] Job depender is empty.");
        }
        return res;
    }

    @Override
    public List<JobDepender> getALLList() {
        List<JobDepender> res = Dao.selectAllDepender();
        if(res.size() == 0){
            Constant.logger.error("[DependerService] Job depender is empty.");
        }
        return res;
    }

    @Override
    public int updateDepender(JobDepender jobDep) {
        int count = Dao.updateDepender(jobDep);
        if(count >= 1){
            Constant.logger.info("[DependerService] Update job depender success.");
        }else {
            Constant.logger.error("[DependerService] Update job depender failed.");
        }
        return count;
    }

    @Override
    public int deleteByDepender(String depJid) {
        int count = Dao.deleteByDepender(depJid);
        if(count >= 1){
            Constant.logger.info("[DependerService] Delete job depender success.");
        }else {
            Constant.logger.error("[DependerService] Delete job depender failed.");
        }
        return count;
    }

    @Override
    public int deleteByJob(String jobId) {
        int count = Dao.deleteByJob(jobId);
        if(count >= 1){
            Constant.logger.info("[DependerService] Delete job depender success.");
        }else {
            Constant.logger.error("[DependerService] Delete job depender failed.");
        }
        return count;
    }


}
