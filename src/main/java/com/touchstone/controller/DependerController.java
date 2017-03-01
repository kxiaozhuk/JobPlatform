package com.touchstone.controller;

import com.touchstone.entity.JobDepender;
import com.touchstone.service.DependerService;
import com.touchstone.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author zhuwenhong on 2016/12/26.
 */
@RestController
@RequestMapping("/depender")
public class DependerController {

    @Autowired
    private DependerService depService;

    /**
     * 根据作业id查询依赖
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity getDependerById(@PathVariable("id") String id) {
        List<JobDepender> deps = depService.getDependerList(id);
        if(deps.size() == 0){
            return new ResponseEntity<>(JacksonUtils.obj2Json(deps), HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(JacksonUtils.obj2Json(deps), HttpStatus.OK);
    }

    /**
     * 返回所有Depender
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public HttpEntity getDependerList() {
        List<JobDepender> deps = depService.getALLList();
        if(deps.size() == 0){
            return new ResponseEntity<>(JacksonUtils.obj2Json(deps), HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(JacksonUtils.obj2Json(deps), HttpStatus.OK);
    }

    /**
     * 更新依赖
     * @param jobDep
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public HttpEntity updateDepender(@RequestBody JobDepender jobDep) {
        int count = depService.updateDepender(jobDep);

        if( count == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    /**
     * 创建依赖
     * @param json
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create/{json}")
    public HttpEntity createDepender(@PathVariable("json") String json) {
        JobDepender jobDep = JacksonUtils.json2Obj(json,JobDepender.class);
        int count = depService.createDepender(jobDep);
        if (count == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 根据作业id删除依赖
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public HttpEntity deleteDepender(@PathVariable("id") String id) {
        int count = depService.deleteByJob(id);

        if (count == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
