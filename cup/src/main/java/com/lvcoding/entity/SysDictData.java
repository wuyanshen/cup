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
 * 字典数据表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_data")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典标签
     */
    @TableField(value = "label")
    private String label;

    /**
     * 字典值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 字典类型编码
     */
    @TableField(value = "type_code")
    private String typeCode;

    /**
     * 状态,1可用0不可用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private Integer tenantId;
}
