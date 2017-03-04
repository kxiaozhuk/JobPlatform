package com.grouptech.controller;

import com.grouptech.entity.TriggerInfo;
import com.grouptech.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author mahone on 2016/12/26.
 */
@Controller
@RequestMapping("/trigger")
public class TriggerController {

    @Autowired
    private TriggerService trgService;

    /**
     * 返回所有Trigger
     * @return
     */
    @GetMapping("list")
    public String getTriggerList(Model model) {
        List<TriggerInfo> trgs = trgService.getTriggerList();
        model.addAttribute("trgList", trgs);
        return "triggerList";
    }

    @GetMapping("add")
    public String addTrigger(Model model) {
        String trgId = UUID.randomUUID().toString().replace("-","");
        model.addAttribute("trgId", trgId);
        return "triggerAdd";
    }

    /**
     * 创建Trigger
     * @param trgInfo
     * @return
     */
    @PostMapping("create")
    public @ResponseBody
    Map<String, Object> createTrigger(@RequestBody TriggerInfo trgInfo) {
        HashMap<String, Object> map = new HashMap<>();
        int count = trgService.createTrigger(trgInfo);
        if( count == 0) {
            map.put("success", false);
            map.put("msg", "新建触发器失败");
            return map;
        } else {
            map.put("success", true);
            map.put("msg", "新建触发器成功");
            return map;
        }
    }


    /**
     * 根据id删除Trigger
     * @param ids
     * @return
     */
    @PostMapping("delete")
    public @ResponseBody
    Map<String, Object> deleteTrigger(@RequestBody List<String> ids) {
        HashMap<String, Object> map = new HashMap<>();
        int delCount = ids.size();
        if(delCount == 0){
            map.put("success", false);
            map.put("msg", "请选择要删除的触发器");
        }
        int allCount = 0;
        for( String id: ids){
            int count = trgService.deleteTrigger(id);
            allCount += count;
        }

        if (delCount == allCount) {
            map.put("success", true);
            map.put("msg", "删除触发器成功");
        } else {
            map.put("success", false);
            map.put("msg", "删除触发器失败");
        }
        return map;
    }

    @GetMapping("{id}/detail")
    public String triggerDetail(@PathVariable("id") String id,Model model) {
        TriggerInfo trgInfo = trgService.getTriggerInfo(id);
        model.addAttribute("trgInfo", trgInfo);
        return "triggerDetail";
    }

    /**
     * 更新trigger
     * @param trgInfo
     * @return
     */
    @PostMapping("update")
    public @ResponseBody
    Map<String, Object> updateTrigger(@RequestBody TriggerInfo trgInfo) {
        HashMap<String, Object> map = new HashMap<>();
        int count = trgService.updateTrigger(trgInfo);
        if( count == 0) {
            map.put("success", false);
            map.put("msg", "更新触发器失败");
            return map;
        } else {
            map.put("success", true);
            map.put("msg", "更新触发器成功");
            return map;
        }
    }



}
