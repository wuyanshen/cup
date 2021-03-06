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

package com.lvcoding.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.PageVO;
import com.lvcoding.entity.vo.ProcessDefinitionVO;
import com.lvcoding.entity.vo.TaskVO;
import com.lvcoding.service.ActivityService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;


    public boolean startActivity() {
        log.info("???????????????....");
        Map map = new HashMap();

        map.put("apply", "zhangsan");

        map.put("approve", "lisi");

        // ????????????
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("leave2", map);
        String processId = pi1.getId();
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task ?????????:{}", task);
        taskService.complete(task.getId(), map);// ?????????????????????
        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task ?????????:{}", task);
        String taskId2 = task.getId();
        map.put("pass", false);
        taskService.complete(taskId2, map);// ????????????
        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task ?????????:{}", task);

        log.info("???????????????....");

        return false;
    }


	@Override
    public PageVO<ProcessDefinitionVO> getDeployList(PageVO<ProcessDefinitionVO> pageVO) {
        // ??????????????????
        int size = NumberUtil.parseInt(pageVO.getSize() + "");
        // ?????????
        int offset = NumberUtil.parseInt((pageVO.getCurrent() - 1) * pageVO.getSize() + "");

        List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery().listPage(offset, size);
        List<ProcessDefinitionVO> collect = list.stream().map(processDefinition -> {
            ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
            BeanUtils.copyProperties(processDefinition, processDefinitionVO);
            return processDefinitionVO;
        }).collect(Collectors.toList());

        // ??????????????????
        long total = repositoryService.createProcessDefinitionQuery().count();
        PageVO<ProcessDefinitionVO> page = new PageVO<>(pageVO.getCurrent(), pageVO.getSize(), total, collect);

        return page;
	}

    @Override
    public void publish() {
        // 3.??????RepositoryService????????????????????????bpmn???png???????????????
        Deployment deployment = repositoryService.createDeployment()
                .name("????????????")
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .deploy();

        // 4.??????????????????
        log.info("???????????????");
        log.info("????????????id???"+deployment.getId());
        log.info("?????????????????????" + deployment.getName());
    }

    @Override
    public void suspendProcess(String processKey) {
        // 3.??????????????????
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processKey) // ????????????key
                .singleResult();
        // 4.?????????????????????????????????
        boolean suspended = processDefinition.isSuspended();

        // 5.?????????????????????????????????????????????????????????????????????
        if(suspended) { // ?????????????????????
            log.info("?????????????????????");
            repositoryService.activateProcessDefinitionByKey(
                    processKey, // ????????????key
                    true, //????????????
                    null // ??????????????????null???????????????
            );
            log.info("???????????????" + processKey + "?????????");
        } else { // ?????????????????????
            log.info("?????????????????????");
            repositoryService.suspendProcessDefinitionByKey(
                    processKey, // ????????????key
                    true, //????????????
                    null // ??????????????????null???????????????
            );
            log.info("???????????????" + processKey + "?????????");
        }
    }

    @Override
    public void publishByXml(DeploymentVO deploymentVO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        // ??????RepositoryService????????????????????????bpmn???png???????????????
        Deployment deployment = repositoryService.createDeployment()
                .name("xml??????" + dateTime)
                .addString(deploymentVO.getName(), deploymentVO.getXml())
                .deploy();

        // ??????????????????
        log.info("??????xml???????????????");
        log.info("????????????id???"+deployment.getId());
        log.info("?????????????????????" + deployment.getName());
    }

    @Override
    public void completeTask(TaskVO taskVO) {

        // 3.????????????id????????????????????????????????????
        // taskService.complete("2505");

        // Task task = taskService.createTaskQuery()
        //         .processDefinitionKey(taskVO.get) // ??????key
        //         .taskAssignee(taskVO.getAssignee()) // ???????????????
        //         .singleResult();
        taskService.complete(taskVO.getId());
    }

    @SneakyThrows
    @Override
    public void publishByZip(MultipartFile multipartFile) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream());
        Deployment deployment = repositoryService.createDeployment()
                .name("zip??????-" + dateTime)
                .addZipInputStream(zipInputStream)
                .deploy();

        // ??????????????????
        log.info("??????zip???????????????");
        log.info("????????????id???"+deployment.getId());
        log.info("?????????????????????" + deployment.getName());
    }

    @Override
    public PageVO<TaskVO> findHistoryTasks(String username, PageVO<TaskVO> pageVO) {
        // ??????????????????
        int size = NumberUtil.parseInt(pageVO.getSize() + "");

        // ?????????
        int offset = NumberUtil.parseInt((pageVO.getCurrent() - 1) * pageVO.getSize() + "");

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processFinished().taskAssignee(username).listPage(offset, size);

        List<TaskVO> collect = historicTaskInstances.stream().map(task -> {
            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            return taskVO;
        }).collect(Collectors.toList());

        // ??????????????????
        long total = historyService.createHistoricTaskInstanceQuery().count();
        PageVO<TaskVO> page = new PageVO<>(pageVO.getCurrent(), pageVO.getSize(), total, collect);

        return page;
    }


    @Override
    public List<TaskVO> findTasks(String username) {
        List<Task> list = taskService.createTaskQuery()
                // .processDefinitionKey(CommonConstant.LEAVE_BILL_KEY)
                .taskAssignee(username)
                .list();

        if (ObjectUtil.isNotEmpty(list)) {
            List<TaskVO> collect = list.stream().map(task -> {
                TaskVO taskVO = new TaskVO();
                BeanUtils.copyProperties(task, taskVO);
                return taskVO;
            }).collect(Collectors.toList());
            return collect;
        }
        return new ArrayList<>();
    }
}
