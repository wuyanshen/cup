package com.lvcoding.activiti;

import com.lvcoding.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot方式使用activiti
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 发布一个流程
     *
     */
    @Test
    public void publishProcess() {
        // 3.使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }


    /**
     * 启动一个实例
     */
    @Test
    public void startProcessInstance() {
        securityUtil.loginAs("admin");
        System.out.println("Number of process definitions : "+ repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
        Map<String, Object> map = new HashMap<>();
        //设置办理人、候选人、候选组
        // map.put("assigneeUserId", "admin");
        map.put("candidateUsers", "李四,王五");
        // map.put("candidateGroups", "group1,group2");
        runtimeService.startProcessInstanceByKey("myLeaveProcess", map);
    }



    /**
     * 拾取任务
     */
    @Test
    public void getProcess() {
        taskService.claim("myLeaveProcess", "");
    }

    /**
     * 查询用户的任务列表
     */
    @Test
    public void taskQuery() {
        securityUtil.loginAs("zhangsan");
        //根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myLeaveProcess")
                .list();

        if(list!=null && list.size()>0){
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

            }
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask(){

        final Task task = taskService.createTaskQuery()
                .processDefinitionKey("myLeaveProcess") // 流程key
                // .taskAssignee("zhangsan") // 任务负责人
                .singleResult();
        taskService.complete(task.getId());
        //任务ID
        System.out.println("完成任务：任务ID：" + task.getId());
    }

    /**
     * 历史活动实例查询
     */
    @Test
    public void queryHistoryTask() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery() // 创建历史活动实例查询
                .processInstanceId("myLeaveProcess") // 执行流程实例id
                .orderByTaskCreateTime()
                .asc()
                .list();
        for (HistoricTaskInstance hai : list) {
            System.out.println("活动ID:" + hai.getId());
            System.out.println("流程实例ID:" + hai.getProcessInstanceId());
            System.out.println("活动名称：" + hai.getName());
            System.out.println("办理人：" + hai.getAssignee());
            System.out.println("开始时间：" + hai.getStartTime());
            System.out.println("结束时间：" + hai.getEndTime());
        }
    }
}
