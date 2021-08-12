package com.lvcoding.activiti;

import cn.hutool.core.io.IoUtil;
import lombok.SneakyThrows;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Java方式使用activiti
 */
public class JavaTest {

    /**
     * 流程定义：ProcessDefinition
     * 静态文件：bpmn、png
     * 例如：请假流程定义
     *
     * 流程实例：ProcessInstance
     * 参与者：
     * 张三 发起请假
     * 李四 发起请假
     * 王五 发起请假
     * 。。。
     *
     * 两者关系，1个流程定义 对应 多个流程实例
     *
     * activiti和实际业务结合方法：
     * activiti表中预留了businessKey字段，用来存储实际的业务表对应的id，这样就将activiti和业务关联起来了
     */



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
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }

    /**
     *  删除流程
     */
    @Test
    public void deleteDeployment() {
        // 1.创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.根据流程部署id删除
        // String id = "1";
        // repositoryService.deleteDeployment(id, true);
        // System.out.println("删除流程部署id" + id);

        // 4.根据流程部署名称删除
        String name = "请假流程";
        Deployment result = repositoryService.createDeploymentQuery()
                .deploymentName(name)
                .singleResult();
        if (result != null) {
            System.out.println("删除流程名称为：" + name);
            // true级联删除，即使有流程实例启动，也可以删除，false非级联删除
            // 项目开发中，级联删除只开放给系统管理员
            repositoryService.deleteDeployment(result.getId(), true);
        } else {
            System.out.println("该流程不存在");
        }
    }


    /**
     *  启动流程实例
     */
    @Test
    public void startProcess() {
        // 1.创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.根据流程定义的id启动流程
        // ProcessInstance instance = runtimeService.startProcessInstanceByKey("Process_1");
        Map<String, Object> map = new HashMap<>();
        map.put("day", 2);
        ProcessInstance instance = runtimeService.startProcessInstanceById("Process_1:1:56931ea6-fb36-11eb-8e45-acde48001122", map);

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
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取TaskService
        TaskService taskService = processEngine.getTaskService();

        // 3.根据流程key和任务负责人查询任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("leave") // 流程key
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
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取TaskService
        TaskService taskService = processEngine.getTaskService();

        // 3.根据任务id完成任务，完成张三的任务
        // taskService.complete("2505");

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("leave") // 流程key
                .taskAssignee("wangwu") // 任务负责人
                .singleResult();
        taskService.complete(task.getId());

        System.out.println("流程实例的ID：" + task.getProcessInstanceId());
        System.out.println("任务ID：" + task.getId());
        System.out.println("任务负责人：" + task.getAssignee());
        System.out.println("任务名称：" + task.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinition() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.查询
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                // .processDefinitionKey("Process_1") // 流程定义key
                .processDefinitionName("请假流程11") // 流程定义名称
                .singleResult();
        if (processDefinition != null) {
            System.out.println("流程定义id：" + processDefinition.getId());
            System.out.println("流程定义名称：" + processDefinition.getName());
            System.out.println("流程定义key：" + processDefinition.getKey());
            System.out.println("流程定义版本：" + processDefinition.getVersion());
            System.out.println("流程部署id：" + processDefinition.getDeploymentId());
        } else {
            System.out.println("没查到相关流程定义");
        }
    }

    /**
     * 流程资源下载
     */
    @SneakyThrows
    @Test
    public void downloadResource() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.下载
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                // .processDefinitionKey("Process_1") // 流程定义key
                .processDefinitionName("请假流程") // 流程定义名称
                .singleResult();

        // 保存png到文件
        InputStream pngStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
        FileOutputStream fosPNG = new FileOutputStream(new File("/Users/wuyanshen/downloads/test.png"));
        IoUtil.copy(pngStream, fosPNG);
        pngStream.close();
        fosPNG.close();


        // 保存bpmn到文件
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        FileOutputStream fosBPMN = new FileOutputStream(new File("/Users/wuyanshen/downloads/test.bpmn"));
        IoUtil.copy(bpmnStream, fosBPMN);
        bpmnStream.close();
        fosBPMN.close();
    }

    /**
     * 流程历史信息查询
     */
    @Test
    public void queryHistory() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取HistoryService
        HistoryService historyService = processEngine.getHistoryService();

        // 3.获取act_hi_actinst表（历史节点表）查询对象
        HistoricActivityInstanceQuery actinstQuery = historyService.createHistoricActivityInstanceQuery();

        List<HistoricActivityInstance> list = actinstQuery.processDefinitionId("Process_1:1:17504")
                .list();
        for (HistoricActivityInstance instance : list) {
            System.out.println("流程名称：" + instance.getActivityName());
            System.out.println("流程id：" + instance.getActivityId());
            System.out.println("流程办理人：" + instance.getAssignee());
            System.out.println("流程类型：" + instance.getActivityType());
            System.out.println("流程定义id：" + instance.getProcessDefinitionId());
            System.out.println("流程实例id：" + instance.getProcessInstanceId());
            System.out.println("===============================================");
        }
    }

    /**
     * 流程定义的挂起和激活
     * 流程定义挂起，它相关的所有流程实例都会挂起
     *
     * 挂起后，表里的SUSPENSION_STATE_的值就是2
     * 激活后，表里的SUSPENSION_STATE_的值就是1
     */
    @Test
    public void processInstanceActive() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.查询流程定义
        String key = "leave";
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                // .processDefinitionName("请假流程")
                .processDefinitionKey(key) // 流程定义key
                .singleResult();
        // 4.获取当前流程定义的状态
        boolean suspended = processDefinition.isSuspended();

        // 5.如果流程是挂起状态就激活，如果是激活状态就挂起
        if(suspended) { // 表示是挂起状态
            System.out.println("目前是挂起状态");
            repositoryService.activateProcessDefinitionByKey(
                    key, // 流程实例key
                    true, //是否激活
                    null // 激活时间，为null就立即激活
            );
            System.out.println("流程定义：" + key + "已激活");
        } else { // 表示是激活状态
            System.out.println("目前是激活状态");
            repositoryService.suspendProcessDefinitionByKey(
                    key, // 流程实例key
                    true, //是否挂起
                    null // 挂起时间，为null就立即挂起
            );
            System.out.println("流程定义：" + key + "已挂起");
        }
    }

    /**
     * 挂起/激活单个流程实例
     * 流程实例挂起，其他的实例还可以正常运行
     *
     * 挂起后，表里的SUSPENSION_STATE_的值就是2
     * 激活后，表里的SUSPENSION_STATE_的值就是1
     */
    @Test
    public void suspendProcessInstance() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.查询流程实例
        String id = "42501";
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(id) // 流程实例id
                .singleResult();
        // 4.获取当前流程定义的状态
        boolean suspended = processInstance.isSuspended();

        // 5.如果流程是挂起状态就激活，如果是激活状态就挂起
        if(suspended) { // 表示是挂起状态
            System.out.println("目前是挂起状态");
            runtimeService.activateProcessInstanceById(id);
            System.out.println("流程实例：" + id + "已激活");
        } else { // 表示是激活状态
            System.out.println("目前是激活状态");
            runtimeService.suspendProcessInstanceById(id);
            System.out.println("流程实例：" + id + "已挂起");
        }
    }

// ==============================================================================================
    /**
     * 使用zip包批量部署流程
     * 启动后会自动创建28张表
     */
    @Test
    public void deployProcessByZip() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.使用RepositoryService部署流程
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("processes/business.zip");
        assert inputStream != null;
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }

    /**
     * 使用xml部署流程
     */
    @Test
    public void deployByXml() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<bpmn:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" id=\"Definitions_1\" targetNamespace=\"http://bpmn.io/schema/bpmn\">\n" +
                "  <bpmn:process id=\"newLeave\" name=\"新的请假流程\" isExecutable=\"true\">\n" +
                "    <bpmn:startEvent id=\"StartEvent_1\" name=\"开始\">\n" +
                "      <bpmn:outgoing>Flow_0aymhqd</bpmn:outgoing>\n" +
                "    </bpmn:startEvent>\n" +
                "    <bpmn:userTask id=\"Activity_1ejk5ls\" name=\"提交请假\" activiti:assignee=\"zhangsan\">\n" +
                "      <bpmn:incoming>Flow_0aymhqd</bpmn:incoming>\n" +
                "      <bpmn:outgoing>Flow_0s6slid</bpmn:outgoing>\n" +
                "    </bpmn:userTask>\n" +
                "    <bpmn:sequenceFlow id=\"Flow_0aymhqd\" sourceRef=\"StartEvent_1\" targetRef=\"Activity_1ejk5ls\" />\n" +
                "    <bpmn:userTask id=\"Activity_0m3jsvi\" name=\"组长审批\" activiti:assignee=\"admin\">\n" +
                "      <bpmn:incoming>Flow_0s6slid</bpmn:incoming>\n" +
                "      <bpmn:outgoing>Flow_00nix90</bpmn:outgoing>\n" +
                "    </bpmn:userTask>\n" +
                "    <bpmn:sequenceFlow id=\"Flow_0s6slid\" sourceRef=\"Activity_1ejk5ls\" targetRef=\"Activity_0m3jsvi\" />\n" +
                "    <bpmn:endEvent id=\"Event_1ehobbm\" name=\"结束\">\n" +
                "      <bpmn:incoming>Flow_00nix90</bpmn:incoming>\n" +
                "    </bpmn:endEvent>\n" +
                "    <bpmn:sequenceFlow id=\"Flow_00nix90\" sourceRef=\"Activity_0m3jsvi\" targetRef=\"Event_1ehobbm\" />\n" +
                "  </bpmn:process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
                "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"newLeave\">\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0aymhqd_di\" bpmnElement=\"Flow_0aymhqd\">\n" +
                "        <di:waypoint x=\"209\" y=\"120\" />\n" +
                "        <di:waypoint x=\"260\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0s6slid_di\" bpmnElement=\"Flow_0s6slid\">\n" +
                "        <di:waypoint x=\"360\" y=\"120\" />\n" +
                "        <di:waypoint x=\"420\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_00nix90_di\" bpmnElement=\"Flow_00nix90\">\n" +
                "        <di:waypoint x=\"520\" y=\"120\" />\n" +
                "        <di:waypoint x=\"582\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
                "        <dc:Bounds x=\"173\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <dc:Bounds x=\"180\" y=\"145\" width=\"22\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1ejk5ls_di\" bpmnElement=\"Activity_1ejk5ls\">\n" +
                "        <dc:Bounds x=\"260\" y=\"80\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_0m3jsvi_di\" bpmnElement=\"Activity_0m3jsvi\">\n" +
                "        <dc:Bounds x=\"420\" y=\"80\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Event_1ehobbm_di\" bpmnElement=\"Event_1ehobbm\">\n" +
                "        <dc:Bounds x=\"582\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <dc:Bounds x=\"589\" y=\"145\" width=\"22\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</bpmn:definitions>";

        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.使用RepositoryService部署流程
        Deployment deployment = repositoryService.createDeployment()
                .addString("leaveXml.bpmn", xml)
                .name("根据xml部署流程")
                .deploy();

        // 4.输出部署信息
        System.out.println("流程部署id "+deployment.getId());
        System.out.println("流程部署名称 " + deployment.getName());
    }
}
