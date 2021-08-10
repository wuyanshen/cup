package com.lvcoding.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.TaskVO;
import com.lvcoding.service.ActivitiService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ActivitiServiceImpl implements ActivitiService {


    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;


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


	@Override
    public List<DeploymentVO> getDeployList() {
		return null;
	}

    @Override
    public void publish() {
        // 3.使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .deploy();

        // 4.输出部署信息
        log.info("流程部署id："+deployment.getId());
        log.info("流程部署名称：" + deployment.getName());
    }


    @Override
    public List<TaskVO> findTasks(String username) {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(CommonConstant.LEAVE_BILL_KEY)
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
        return null;
    }
}
