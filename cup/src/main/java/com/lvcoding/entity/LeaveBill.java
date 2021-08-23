/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请假单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "leave_bill")
public class LeaveBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 请假类型(1.年假2.事假3.病假 4.调休假)
     */
    @TableField(value = "type")
    private String type;

    /**
     * 请假原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 开始时间
     */
    @TableField(value = "start_date")
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField(value = "end_date")
    private Date endDate;

    /**
     * 审批意见
     */
    @TableField(value = "advise")
    private String advise;

    /**
     * 请假状态(0待审核 -1驳回 -2不通过 1通过)
     */
    @TableField(value = "status")
    private String status;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private Integer tenantId;


}
