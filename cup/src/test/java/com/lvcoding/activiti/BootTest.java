package com.lvcoding.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lvcoding.entity.vo.DeploymentVO;

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
     * 影响的表：
     * act_ge_bytearray
     * act_re_deployment
     * act_re_procdef
     *
     */
    @Test
    public void publishProcess() {
        // 3.使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }


    /**
     * 启动一个实例
     * 影响的表：
     * act_hi_actinst
     * act_hi_detail
     * act_hi_identitylink
     * act_hi_procinst
     * act_hi_taskinst
     * act_hi_varinst
     *
     * act_ru_execution
     * act_ru_identitylink
     * act_ru_task
     * act_ru_variable
     *
     */
    @Test
    public void startProcessInstance() {
        // securityUtil.loginAs("admin");
        System.out.println("Number of process definitions : "+ repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
         Map<String, Object> map = new HashMap<>();
        //设置办理人、候选人、候选组
        // map.put("assigneeUserId", "admin");
        // map.put("candidateUsers", "李四,王五");
        // map.put("candidateGroups", "group1,group2");
         map.put("assignee1", "zhangsan");
         map.put("assignee2", "jl");
        runtimeService.startProcessInstanceByKey("myProcess_1", map);
    }



    /**
     * 拾取任务
     */
    @Test
    public void getProcess() {
        taskService.claim("myProcess_1", "");
    }

    /**
     * 查询用户的任务列表
     */
    @Test
    public void taskQuery() {
         securityUtil.loginAs("jl");
        //根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1")
//                .taskCandidateUser("zhangsan")
                .taskAssignee("zhangsan")
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
                .processDefinitionKey("myProcess_1") // 流程key
//                .taskCandidateUser("zhangsan")
                 .taskAssignee("jl") // 任务负责人
                .singleResult();
        if (task != null) {
            taskService.complete(task.getId());
            //任务ID
            System.out.println("完成任务：任务ID：" + task.getId());
        } else {
            System.out.println("没有需要完成的任务！");
        }

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

    /**
     * 流程删除
     */
    @Test
    public void deleteDeployment(){
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentName("")
                .singleResult();
        //1、创建 ProcessEngine
        // 通过流程引擎获取repositoryService
        //删除流程定义，如果该流程定义已经有流程实例启动，则删除出错
        repositoryService.deleteDeployment("7b86176b-f276-11eb-b033-00ff65f53676",true);
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式
        // repositoryService.deleteDeployment("1", true);
    }

    /**
     * 查询发布的流程列表
     */
    @Test
    public void findDeploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        List<DeploymentVO> collect = list.stream().map(dep -> {
            DeploymentVO deploymentVO = new DeploymentVO();
            BeanUtils.copyProperties(dep, deploymentVO);
            return deploymentVO;
        }).collect(Collectors.toList());

        System.out.println("列表的大小：" + collect.size());
        collect.forEach(System.out::print);
    }
}
