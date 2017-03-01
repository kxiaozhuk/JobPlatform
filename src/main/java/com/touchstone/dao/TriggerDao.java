package com.touchstone.dao;

import com.touchstone.entity.TriggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@Mapper
public interface TriggerDao {
    public int countAllTrigger();
    public TriggerInfo selectTriggerById(@Param("trgId") String trgId);
    public int insertTrigger(@Param("trgInfo") TriggerInfo trgInfo);
    public int deleteTriggerById(@Param("trgId") String trgId);
    public List<TriggerInfo> selectAllTrigger();
    public int updateTrigger(@Param("trgInfo") TriggerInfo trgInfo);
    public List<TriggerInfo> selectTriggerByIf(@Param("trgInfo") TriggerInfo trgInfo);

}
