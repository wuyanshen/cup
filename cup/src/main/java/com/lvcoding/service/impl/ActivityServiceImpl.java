package com.lvcoding.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.vo.DeploymentVO;
import com.lvcoding.entity.vo.PageVO;
import com.lvcoding.entity.vo.ProcessDefinitionVO;
import com.lvcoding.entity.vo.TaskVO;
import com.lvcoding.service.ActivityService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;


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
    public PageVO<ProcessDefinitionVO> getDeployList(PageVO<ProcessDefinitionVO> pageVO) {
        // 每页显示条数
        int size = NumberUtil.parseInt(pageVO.getSize() + "");
        // 偏移量
        int offset = NumberUtil.parseInt((pageVO.getCurrent() - 1) * pageVO.getSize() + "");

        List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery().listPage(offset, size);
        List<ProcessDefinitionVO> collect = list.stream().map(processDefinition -> {
            ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
            BeanUtils.copyProperties(processDefinition, processDefinitionVO);
            return processDefinitionVO;
        }).collect(Collectors.toList());

        // 组装分页参数
        long total = repositoryService.createProcessDefinitionQuery().count();
        PageVO<ProcessDefinitionVO> page = new PageVO<>(pageVO.getCurrent(), pageVO.getSize(), total, collect);

        return page;
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
        log.info("部署工作流");
        log.info("流程部署id："+deployment.getId());
        log.info("流程部署名称：" + deployment.getName());
    }

    @Override
    public void suspendProcess(String processKey) {
        // 3.查询流程定义
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processKey) // 流程定义key
                .singleResult();
        // 4.获取当前流程定义的状态
        boolean suspended = processDefinition.isSuspended();

        // 5.如果流程是挂起状态就激活，如果是激活状态就挂起
        if(suspended) { // 表示是挂起状态
            log.info("目前是挂起状态");
            repositoryService.activateProcessDefinitionByKey(
                    processKey, // 流程实例key
                    true, //是否激活
                    null // 激活时间，为null就立即激活
            );
            log.info("流程定义：" + processKey + "已激活");
        } else { // 表示是激活状态
            log.info("目前是激活状态");
            repositoryService.suspendProcessDefinitionByKey(
                    processKey, // 流程实例key
                    true, //是否挂起
                    null // 挂起时间，为null就立即挂起
            );
            log.info("流程定义：" + processKey + "已挂起");
        }
    }

    @Override
    public void publishByXml(DeploymentVO deploymentVO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        // 使用RepositoryService进行流程部署，把bpmn和png存到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("xml流程" + dateTime)
                .addString(deploymentVO.getName(), deploymentVO.getXml())
                .deploy();

        // 输出部署信息
        log.info("通过xml部署工作流");
        log.info("流程部署id："+deployment.getId());
        log.info("流程部署名称：" + deployment.getName());
    }

    @Override
    public void completeTask(TaskVO taskVO) {

        // 3.根据任务id完成任务，完成张三的任务
        // taskService.complete("2505");

        // Task task = taskService.createTaskQuery()
        //         .processDefinitionKey(taskVO.get) // 流程key
        //         .taskAssignee(taskVO.getAssignee()) // 任务负责人
        //         .singleResult();
        taskService.complete(taskVO.getId());
    }

    @SneakyThrows
    @Override
    public void publishByZip(MultipartFile multipartFile) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream());
        Deployment deployment = repositoryService.createDeployment()
                .name("zip流程-" + dateTime)
                .addZipInputStream(zipInputStream)
                .deploy();

        // 输出部署信息
        log.info("通过zip部署工作流");
        log.info("流程部署id："+deployment.getId());
        log.info("流程部署名称：" + deployment.getName());
    }

    @Override
    public PageVO<TaskVO> findHistoryTasks(String username, PageVO<TaskVO> pageVO) {
        // 每页显示条数
        int size = NumberUtil.parseInt(pageVO.getSize() + "");

        // 偏移量
        int offset = NumberUtil.parseInt((pageVO.getCurrent() - 1) * pageVO.getSize() + "");

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processFinished().taskAssignee(username).listPage(offset, size);

        List<TaskVO> collect = historicTaskInstances.stream().map(task -> {
            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            return taskVO;
        }).collect(Collectors.toList());

        // 组装分页参数
        long total = historyService.createHistoricTaskInstanceQuery().count();
        PageVO<TaskVO> page = new PageVO<>(pageVO.getCurrent(), pageVO.getSize(), total, collect);

        return page;
    }


    @Override
    public List<TaskVO> findTasks(String username) {
        List<Task> list = taskService.createTaskQuery()
                // .processDefinitionKey(CommonConstant.LEAVE_BILL_KEY)
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
        return new ArrayList<>();
    }
}
