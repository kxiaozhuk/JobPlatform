package com.grouptech.controller;

import com.grouptech.entity.JobDepender;
import com.grouptech.entity.JobInfo;
import com.grouptech.entity.TriggerInfo;
import com.grouptech.service.DependerService;
import com.grouptech.service.JobService;
import com.grouptech.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author mahone on 2016/12/26.
 */
@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private TriggerService trgService;
    @Autowired
    private DependerService depService;

    /**
     * 返回所有Job
     * @return
     */
    @GetMapping("list")
    public String getJobList(Model model) {
        List<JobInfo> jobs = jobService.getJobList();
        model.addAttribute("jobList", jobs);
        return "jobList";
    }

    @GetMapping("add")
    public String addJob(Model model) {
        String jobId = UUID.randomUUID().toString().replace("-","");
        model.addAttribute("jobId", jobId);
        List<TriggerInfo> trigs = trgService.getTriggerList();
        model.addAttribute("trgList", trigs);
        List<JobInfo> jobs = jobService.getJobList();
        model.addAttribute("jobList", jobs);
        return "jobAdd";
    }

    /**
     * 创建作业
     * @param jobInfo
     * @return
     */
    @PostMapping("create")
    public @ResponseBody
    Map<String, Object> createJob(@RequestBody JobInfo jobInfo) {
        //JobInfo jobInfo = JacksonUtils.json2Obj(json,JobInfo.class);
        HashMap<String, Object> map = new HashMap<>();
        int count = jobService.createJob(jobInfo);
        if( count == 0) {
            map.put("success", false);
            map.put("msg", "新建作业失败");
            return map;
        } else {
            map.put("success", true);
            map.put("msg", "新建作业成功");
            return map;
        }
    }

    /**
     * 根据id删除作业
     * @param ids
     * @return
     */
    @PostMapping("delete")
    public @ResponseBody
    Map<String, Object> deleteJob(@RequestBody List<String> ids) {
        HashMap<String, Object> map = new HashMap<>();
        int delCount = ids.size();
        if(delCount == 0){
            map.put("success", false);
            map.put("msg", "请选择要删除的作业");
        }
        int allCount = 0;
        for( String id: ids){
            int count = jobService.deleteJob(id);
            allCount += count;
        }

        if (delCount == allCount) {
            map.put("success", true);
            map.put("msg", "删除作业成功");
        } else {
            map.put("success", false);
            map.put("msg", "删除作业失败");
        }
        return map;
    }

    /**
     * 运行作业
     * @param id
     * @return
     */
    @PostMapping("{id}/run")
    public @ResponseBody
    Map<String, Object> runJob(@PathVariable(value = "id") String id) {
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = jobService.runJob(id);
        if( flag) {
            map.put("success", true);
            map.put("msg", "开始运行作业");
        } else {
            map.put("success", false);
            map.put("msg", "运行作业失败");
        }
        return map;
    }

    /**
     * 暂停作业
     * @param id
     * @return
     */
    @PostMapping("{id}/stop")
    public @ResponseBody
    Map<String, Object> stopJob(@PathVariable(value = "id") String id) {
        HashMap<String, Object> map = new HashMap<>();
        Boolean flag = jobService.stopJob(id);
        if( flag ) {
            map.put("success", true);
            map.put("msg", "停止作业成功");
        } else {
            map.put("success", false);
            map.put("msg", "停止作业失败");
        }
        return map;
    }

    @GetMapping("{id}/detail")
    public String jobDetail(@PathVariable("id") String id,Model model) {
        JobInfo job = jobService.getJobInfo(id);
        model.addAttribute("jobInfo", job);
        List<TriggerInfo> trigs = trgService.getTriggerList();
        model.addAttribute("trgList", trigs);
        List<JobInfo> jobs = jobService.getJobList();
        model.addAttribute("jobList", jobs);
        List<JobDepender> deps = depService.getDependerList(id);
        List<String> depList = new ArrayList<>();
        deps.forEach(j->depList.add(j.getDepJid()) );
        model.addAttribute("depList",depList);
        return "jobDetail";
    }

    /**
     * 更新作业
     * @param jobInfo
     * @return
     */
    @PostMapping("update")
    public @ResponseBody
    Map<String, Object> updateJob(@RequestBody JobInfo jobInfo) {
        HashMap<String, Object> map = new HashMap<>();
        int count = jobService.updateJob(jobInfo);
        if( count == 0) {
            map.put("success", false);
            map.put("msg", "更新作业失败");
            return map;
        } else {
            map.put("success", true);
            map.put("msg", "更新作业成功");
            return map;
        }
    }

}
