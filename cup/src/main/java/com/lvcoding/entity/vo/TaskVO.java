package com.lvcoding.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuyanshen
 * @discription 任务实体
 * @date 2021-08-10 下午2:58
 */
@Data
public class TaskVO implements Serializable {

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务的创建时间
     */
    private Date createTime;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 执行对象ID
     */
    private String executionId;

    /**
     * 任务类型
     */
    private String category;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务的办理人
     */
    private String assignee;

}
