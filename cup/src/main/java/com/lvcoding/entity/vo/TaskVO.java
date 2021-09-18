/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuyanshen
 * @description 任务实体
 * @date 2021-08-10 下午2:58
 */
@Data
public class TaskVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 任务完成时间
     */
    private Date endTime;

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
