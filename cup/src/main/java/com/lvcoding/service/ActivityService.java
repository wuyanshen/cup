package com.lvcoding.service;

import com.lvcoding.entity.vo.DeploymentVO;
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
    List<ProcessDefinitionVO> getDeployList();

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
}
