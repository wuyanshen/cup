/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.PageVO;
import com.lvcoding.entity.vo.ProcessDefinitionVO;
import com.lvcoding.entity.vo.TaskVO;
import com.lvcoding.service.ActivityService;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2021-02-02 上午11:47
 */
@Slf4j
@RequestMapping("act")
@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    /**
     * 发布流程
     */
    @PostMapping("publish")
    public Res publish() {
        this.activityService.publish();
        return Res.success("发布成功");
    }

    /**
     * 根据xml发布流程
     */
    @PostMapping("publishByXml")
    public Res publishByXml(@RequestBody DeploymentVO deploymentVO) {
        this.activityService.publishByXml(deploymentVO);
        return Res.success("发布成功");
    }

    /**
     * 根据zip发布流程
     */
    @PostMapping("publishByZip")
    public Res publishByZip(@RequestParam("file") MultipartFile multipartFile) {
        this.activityService.publishByZip(multipartFile);
        return Res.success("发布成功");
    }

    /**
     * 删除流程
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id") String id) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
        if(ObjectUtil.isNotEmpty(deployment)) {
            repositoryService.deleteDeployment(deployment.getId(), true);
            return Res.success("删除流程成功");
        } else {
            return Res.success("不存在该流程");
        }
    }

    /**
     * 查询待办任务
     * @param username
     * @return
     */
    @GetMapping
    public Res find(@RequestParam("username") String username) {
        List<TaskVO> list = activityService.findTasks(username);
        if (list != null) {
            return Res.success(list);
        } else {
            return Res.success("没有待办任务");
        }
    }

    /**
     * 查询历史已办任务
     * @param username
     * @return
     */
    @GetMapping("findHistoryTasks")
    public Res findHistoryTask(@RequestParam("username") String username, PageVO<TaskVO> pageVO) {
        PageVO<TaskVO> page = activityService.findHistoryTasks(username, pageVO);
        return Res.success(page);
    }


    /**
     * 查询部署中的工作流
     */
    @GetMapping("deps")
    public Res deps(PageVO<ProcessDefinitionVO> pageVO) {
        return Res.success(this.activityService.getDeployList(pageVO));
    }

    /**
     * 挂起/激活流程
     */
    @PutMapping("suspend")
    public Res suspend(@RequestBody ProcessDefinitionVO processDefinitionVO) {
        this.activityService.suspendProcess(processDefinitionVO.getKey());
        return Res.success();
    }

    /**
     * 处理任务
     */
    @PutMapping("complete")
    public Res complete(@RequestBody TaskVO taskVO) {
        this.activityService.completeTask(taskVO);
        return Res.success();
    }
}
