package com.lvcoding.service;

import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.TaskVO;

import java.util.List;

public interface ActivitiService {

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
    List<DeploymentVO> getDeployList();

    /**
     * 发布流程
     *
     * @param
     */
    void publish();
}
