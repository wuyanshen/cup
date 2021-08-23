/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.service;

import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.PageVO;
import com.lvcoding.entity.vo.ProcessDefinitionVO;
import com.lvcoding.entity.vo.TaskVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService {

    /**
     * 查询待办任务
     *
     * @param username
     */
    List<TaskVO> findTasks(String username);

    /**
     * 查询部署列表
     *
     * @param
     */
    PageVO<ProcessDefinitionVO> getDeployList(PageVO<ProcessDefinitionVO> pageVO);

    /**
     * 发布流程
     *
     * @param
     */
    void publish();

    /**
     * 挂起/激活流程
     *
     * @param processKey
     */
    void suspendProcess(String processKey);

    /**
     * 根据xml发布流程
     *
     * @param deploymentVO
     */
    void publishByXml(DeploymentVO deploymentVO);

    /**
     * 处理任务
     *
     * @param taskVO
     */
    void completeTask(TaskVO taskVO);


    void publishByZip(MultipartFile multipartFile);

    /**
     * 查询历史已办理任务
     *  @param username
     * @param pageVO
     * @return
     */
    PageVO<TaskVO> findHistoryTasks(String username, PageVO<TaskVO> pageVO);
}
