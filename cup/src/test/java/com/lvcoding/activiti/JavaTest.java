package com.lvcoding.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Java方式使用activiti
 */
public class JavaTest {



    /**
     * 初始化数据库
     */
    @Test
    public void initDatabase(){
        // 1.创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 流程部署
     * 启动后会自动创建28张表
     */
    @Test
    public void testDeployment() {
        // 1.创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("出差申请流程")
                .addClasspathResource("processes/business.bpmn")
                .addClasspathResource("processes/business.png")
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }


    /**
     *  启动流程实例
     */
    @Test
    public void startProcess() {
        // 1.创建ProcessEngine
        final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RuntimeService
        final RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.根据流程定义的id启动流程
        final ProcessInstance instance = runtimeService.startProcessInstanceByKey("myBusiness");

        // 4.输出内容
        System.out.println("流程定义ID：" + instance.getProcessDefinitionId());
        System.out.println("流程实例ID：" + instance.getId());
        System.out.println("当前活动的ID：" + instance.getActivityId());
    }

    /**
     * 查询个人的任务列表
     *
     * 底层执行的sql语句
     * select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = 'zhangsan' and D.KEY_ = 'myBusiness' order by RES.ID_ asc LIMIT 2147483647 OFFSET 0;
     */
    @Test
    public void findPersonTaskList() {
        // 1.获取流程引擎
        final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取TaskService
        final TaskService taskService = processEngine.getTaskService();

        // 3.根据流程key和任务负责人查询任务
        final List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("myBusiness") // 流程key
                .taskAssignee("zhangsan") // 任务负责人
                .list();

        // 4.输出
        for (Task task : taskList) {
            System.out.println("流程实例的ID：" + task.getProcessInstanceId());
            System.out.println("任务ID：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        // 1.获取流程引擎
        final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取TaskService
        final TaskService taskService = processEngine.getTaskService();

        // 3.根据任务id完成任务，完成张三的任务
        // taskService.complete("2505");

        final Task task = taskService.createTaskQuery()
                .processDefinitionKey("myBusiness") // 流程key
                .taskAssignee("王五") // 任务负责人
                .singleResult();
        taskService.complete(task.getId());

        System.out.println("流程实例的ID：" + task.getProcessInstanceId());
        System.out.println("任务ID：" + task.getId());
        System.out.println("任务负责人：" + task.getAssignee());
        System.out.println("任务名称：" + task.getName());
    }

// ==============================================================================================
    /**
     * 使用zip包批量部署流程
     * 启动后会自动创建28张表
     */
    @Test
    public void deployProcessByZip() {
        // 1.获取流程引擎
        final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        final RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.使用RepositoryService部署流程
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("processes/business.zip");
        assert inputStream != null;
        final ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        final Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }
}
