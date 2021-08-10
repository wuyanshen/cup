package com.lvcoding.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.ProcessDefinitionVO;
import com.lvcoding.entity.vo.TaskVO;
import com.lvcoding.service.ActivityService;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuyanshen
 * @discription 描述
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
     * 删除流程
     * @param name
     * @return
     */
    @DeleteMapping("{name}")
    public Res delete(@PathVariable("name") String name) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentName(name).singleResult();
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
     * 审批任务
     */
    @PutMapping("complete")
    public Res complete(@RequestParam("taskId") String taskId) {
        this.taskService.complete(taskId);
        return Res.success("审批成功");
    }

    /**
     * 查询部署中的工作流
     */
    @GetMapping("deps")
    public Res deps() {
        return Res.success(this.activityService.getDeployList());
    }
}
