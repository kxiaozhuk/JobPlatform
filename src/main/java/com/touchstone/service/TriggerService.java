package com.touchstone.service;

import com.touchstone.entity.TriggerInfo;
import org.quartz.Trigger;
import java.util.List;

/**
 * @author zhuwenhong on 2016/12/26.
 */
public interface TriggerService {

    public TriggerInfo getTriggerInfo(String trgId);
    public List<TriggerInfo> getTriggerList();
    public int deleteTrigger(String trgId);
    public int updateTrigger(TriggerInfo trgInfo);
    public int createTrigger(TriggerInfo trgInfo);
    // 默认trgName传入jobId，trgGroup传入trgId
    public Trigger getTrigger(String trgName, String trgGroup);
    public Trigger initialTrigger(String trgName, String trgGroup);
    public int countAll();
}
