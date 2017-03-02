package com.springboot.service.impl;

import com.springboot.dao.TriggerDao;
import com.springboot.entity.TriggerInfo;
import com.springboot.service.TriggerService;
import com.springboot.util.Constant;
import com.springboot.util.SchedulerUtils;
import org.quartz.*;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Calendar;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@Service("TriggerService")
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    private TriggerDao tDao;

    /**
     * 根据id查找调度器
     * @param trgId
     * @return
     */
    @Override
    public TriggerInfo getTriggerInfo(String trgId) {
        TriggerInfo trg = tDao.selectTriggerById(trgId);
        if(trg == null){
            Constant.logger.error("[TriggerService] No such trigger by id: "+ trgId);
        }
        return trg;
    }

    /**
     * 返回所有Trigger
     * @return
     */
    @Override
    public List<TriggerInfo> getTriggerList() {
        List<TriggerInfo> trgs = tDao.selectAllTrigger();
        if(trgs.size() == 0){
            Constant.logger.error("[TriggerService] job trigger is empty.");
        }
        return trgs;
    }

    /**
     * 增加触发器
     * @param trgInfo
     */
    @Override
    public int createTrigger(TriggerInfo trgInfo) {
        if ( trgInfo.getTrgId() == null || trgInfo.getTrgId() == ""){
            trgInfo.setTrgId(UUID.randomUUID().toString().replace("-",""));
        }
        int count = tDao.insertTrigger(trgInfo);
        return count;
    }



    /**
     * 初始化触发器
     * @param trgName 默认trgName传入jobId
     * @param trgGroup 默认trgGroup传入trgId
     * @return Trigger
     */
    @Override
    public Trigger initialTrigger(String trgName, String trgGroup) {
        Trigger trigger;
        try {
            TriggerInfo trgInfo = getTriggerInfo(trgGroup);
            // Cron调度器
            CronScheduleBuilder schedBuilder = createSchedBuilder(trgInfo);
            // 开始运行日期和结束运行日期
            Date beginDate = new Date(trgInfo.getBegnDt().getTime());
            Date endDate = new Date(trgInfo.getEndDt().getTime());
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            // 不运行日期 是否为空判定
            if( trgInfo.getSkipDt() == null || "".equals(trgInfo.getSkipDt())) {
                // 触发器
                trigger = newTrigger()
                        .withIdentity(trgName, trgGroup)
                        .withSchedule(schedBuilder)
                        .startAt(beginDate)
                        .endAt(endDate)
                        .build();
            } else {
                Date skipDate = new Date(trgInfo.getSkipDt().getTime());
                HolidayCalendar cal = new HolidayCalendar();
                cal.addExcludedDate(skipDate);
                sched.addCalendar(trgGroup,cal,true,true);

                // 触发器
                trigger = newTrigger()
                        .withIdentity(trgName, trgGroup)
                        .withSchedule(schedBuilder)
                        .startAt(beginDate)
                        .endAt(endDate)
                        .modifiedByCalendar(trgGroup)
                        .build();
            }
        } catch (Exception e) {
            Constant.logger.error("[TriggerService] Initial trigger failed.",e);
            throw new RuntimeException(e);
        }
        return trigger;
    }


    /**
     * 获得调度器
     * @param trgName 默认trgName传入jobId
     * @param trgGroup 默认trgGroup传入trgId
     * @return Trigger
     */
    @Override
    public Trigger getTrigger(String trgName, String trgGroup) {
        Trigger trigger;
        try {
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            TriggerKey tKey = new TriggerKey(trgName, trgGroup);
            // 存在则返回，否则初始化一个触发器
            if ( sched.checkExists(tKey) ){
                trigger = sched.getTrigger(tKey);
            } else {
                trigger = initialTrigger(trgName,trgGroup);
            }
        } catch (Exception e) {
            Constant.logger.error("[TriggerService] Get trigger failed.",e);
            throw new RuntimeException(e);
        }
        return trigger;
    }

    /**
     * 更新调度器
     * @param trgInfo
     * @return int
     */
    @Override
    public int updateTrigger(TriggerInfo trgInfo) {
        int count;
        String trgGroup = trgInfo.getTrgId();
        try {
            /************************先比对更新Trigger************************/
            // 判断触发器是否已经绑定作业，如绑定需rescheduleJob，否则直接更新数据库
            if(tDao.countTriggerById(trgGroup) >=1){
                // 开始运行日期
                Date beginDate = new Date(trgInfo.getBegnDt().getTime());
                // 结束运行日期
                Date endDate = new Date(trgInfo.getEndDt().getTime());
                // 不运行时点
                Date skipDt = new Date(trgInfo.getSkipDt().getTime());
                // 运行周期和运行时点
                CronScheduleBuilder schedBuilder = createSchedBuilder(trgInfo);
                // 根据Trigger组获取所有Triger Key
                GroupMatcher<TriggerKey> GMTkey = GroupMatcher.triggerGroupEquals(trgGroup);
                Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
                // 日历：用于排除某一天
                HolidayCalendar cal = (HolidayCalendar) sched.getCalendar(trgGroup);
                Date calDate = cal.getExcludedDates().first();
                Set<TriggerKey> tKeys = sched.getTriggerKeys(GMTkey);
                // 修改所有Triger Key并重新恢复触发器（如果有变动）
                for(TriggerKey tKey : tKeys){
                    CronTrigger oldTrigger = (CronTrigger) sched.getTrigger(tKey);
                    TriggerBuilder tb = oldTrigger.getTriggerBuilder();
                    // 开始运行日期判断更新
                    if (oldTrigger.getStartTime() != beginDate){
                        tb = tb.startAt(beginDate);
                    }
                    // 结束运行日期判断更新
                    if (oldTrigger.getEndTime() != endDate){
                        tb = tb.endAt(endDate);
                    }
                    // 运行周期和运行时点判断更新
                    if( !schedBuilder.equals(oldTrigger.getScheduleBuilder()) ){
                        tb = tb.withSchedule(schedBuilder);
                    }
                    // 不运行时点判断更新
                    if( calDate != skipDt ){
                        cal.removeExcludedDate(calDate);
                        cal.addExcludedDate(skipDt);
                        sched.addCalendar(trgGroup,cal,true,true);
                        tb = tb.modifiedByCalendar(trgGroup);
                    }
                    // 重新恢复触发器
                    sched.rescheduleJob(tKey, tb.build());
                }
            }
            /************************再更新数据库记录************************/
            count = tDao.updateTrigger(trgInfo);
            if(count >= 1){
                Constant.logger.info("[TriggerService] Update trigger success.");
            }else {
                Constant.logger.error("[TriggerService] Update trigger failed because of record is empty in database.");
            }
        } catch (Exception e) {
            Constant.logger.error("[TriggerService] Update trigger failed.",e);
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * 删除调度器
     * @param trgId
     */
    @Override
    public int deleteTrigger(String trgId) {
        int count;
        try {
            // 先移除Quartz中的触发器
            // 根据Trigger组获取所有Triger Key
            GroupMatcher<TriggerKey> GMTkey = GroupMatcher.triggerGroupEquals(trgId);
            Scheduler sched = SchedulerUtils.getScheduler(Constant.OWNER);
            sched.pauseTriggers(GMTkey); // 停止触发器
            Set<TriggerKey> tKeys = sched.getTriggerKeys(GMTkey);
            for(TriggerKey tKey : tKeys) {
                sched.unscheduleJob(tKey); // 移除触发器
            }
            // 再删除数据库记录
            count = tDao.deleteTriggerById(trgId);
            if(count >= 1){
                Constant.logger.info("[TriggerService] Delete Trigger success.");
            }else {
                Constant.logger.error("[TriggerService] Delete trigger failed because of record is empty in database.");
            }
        } catch (Exception e) {
            Constant.logger.error("[TriggerService] Delete trigger failed.",e);
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int countAll() {
        return tDao.countAllTrigger();
    }

    private CronScheduleBuilder createSchedBuilder(TriggerInfo trgInfo){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trgInfo.getRunDayTm());
        // Cron调度器
        CronScheduleBuilder schedBuilder;
        if ( trgInfo.getRunCycl() == "daily") {
            schedBuilder = CronScheduleBuilder.dailyAtHourAndMinute(calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE));
        } else if (trgInfo.getRunCycl() == "weekly" && trgInfo.getRunDayWeek() != 0){
            schedBuilder = CronScheduleBuilder.weeklyOnDayAndHourAndMinute(trgInfo.getRunDayWeek(),calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE));
        }else if (trgInfo.getRunCycl() == "monthly" && trgInfo.getRunDayMon() != 0) {
            schedBuilder = CronScheduleBuilder.monthlyOnDayAndHourAndMinute(trgInfo.getRunDayMon(),calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE));
        }else {
            schedBuilder = null;
        }
        return schedBuilder;
    }


}
