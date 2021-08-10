package com.lvcoding.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuyanshen
 * @discription 流程定义实体
 * @date 2021-08-10 下午5:28
 */
@Data
public class ProcessDefinitionVO implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 从定义元素中的 targetNamespace 属性派生的类别名称
     */
    private String category;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程定义key
     */
    private String key;

    /**
     * 流程描述
     **/
    private String description;

    /**
     * 流程定义版本
     */
    private int version;

    /**
     * 流程定义bpmn资源名称
     */
    private String resourceName;

    /**
     * 流程部署id
     */
    private String deploymentId;

    /**
     * 流程定义图片资源名称
     */
    private String diagramResourceName;

    /**
     * 流程定义是否有startFormKey.
     */
    private boolean hasStartFormKey;

    /**
     * 此流程定义是否定义了图形符号（以便可以生成图表）？
     */
    private boolean hasGraphicalNotation;

    /**
     * 流程定义是否已挂起
     */
    private boolean suspended;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 流程引擎版本
     */
    String engineVersion;
}
