package com.lvcoding.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.activiti.ActivityService;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 发布流程
     */
    @PostMapping("publish")
    public Res publish() {
        // 3.使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .deploy();

        // 4.输出部署信息
        log.info("流程部署id："+deployment.getId());
        log.info("流程部署名称：" + deployment.getName());

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
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1")
                .taskAssignee(username)
                .list();

        if(list!=null && list.size()>0){
            List<Map<String,Object>> newList = new ArrayList<>();

            for(Task task:list){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("任务的属性:"+task.getProcessVariables());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("getOwner:"+task.getOwner());
                System.out.println("getCategory:"+task.getCategory());
                System.out.println("getDescription:"+task.getDescription());
                System.out.println("getFormKey:"+task.getFormKey());
                Map<String, Object> map = task.getProcessVariables();
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }
                for (Map.Entry<String, Object> m : task.getTaskLocalVariables().entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }

                Map<String,Object> newMap =  new HashMap<>();
                newMap.put("taskId",task.getId());
                newMap.put("taskName",task.getName());
                newMap.put("createTime",task.getCreateTime());
                newList.add(newMap);

            }

            return Res.success(newList);
        }

        return Res.success("没有待办任务");
    }

    @PutMapping("complete")
    public Res complete(@RequestParam("taskId") String taskId) {
        this.taskService.complete(taskId);
        return Res.success("审批成功");
    }
}
