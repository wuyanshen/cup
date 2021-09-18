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

package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant")
public class SysTenant extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 租户名称
     */
    @TableField(value = "tenant_name")
    private String tenantName;

    /**
     * 租户编号
     */
    @TableField(value = "tenant_id")
    private Integer tenantId;


    private static final long serialVersionUID = 1L;
}
