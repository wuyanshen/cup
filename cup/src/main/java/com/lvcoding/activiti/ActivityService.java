package com.lvcoding.activiti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityService {

    private final RuntimeService runtimeService;

    private final TaskService taskService;


    public boolean startActivity() {
        log.info("工作流启动....");
        Map map = new HashMap();

        map.put("apply", "zhangsan");

        map.put("approve", "lisi");

        // 流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("leave2", map);
        String processId = pi1.getId();
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task 第一步:{}", task);
        taskService.complete(task.getId(), map);// 完成第一步申请
        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task 第二步:{}", task);
        String taskId2 = task.getId();
        map.put("pass", false);
        taskService.complete(taskId2, map);// 驳回申请
        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        log.info("task 第三步:{}", task);

        log.info("工作流结束....");

        return false;
    }


}
