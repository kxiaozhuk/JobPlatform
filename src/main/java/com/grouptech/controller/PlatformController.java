package com.grouptech.controller;

import com.grouptech.service.JobService;
import com.grouptech.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author mahone on 2017/1/3.
 */
@Controller
public class PlatformController {

    @Autowired
    private JobService jobService;
    @Autowired
    private TriggerService trgService;

    @RequestMapping("/index")
    public String index(Model model) {
        Map<String,String> jobCount = jobService.groupByStatus();
        int trgCount = trgService.countAll();
        model.addAttribute("jobCount", jobCount);
        model.addAttribute("trgCount", trgCount);
        return "index";
    }
}
