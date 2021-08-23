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

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 组织机构表(SysOrg)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_org")
public class SysOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父节点id
     */
    @TableField(value = "org_pid")
    private Integer orgPid;

    /**
     * 所有的父节点
     */
    @TableField(value = "org_pids")
    private String orgPids;

    /**
     * 是否叶子节点
     */
    @TableField(value = "is_leaf")
    private Integer isLeaf;

    /**
     * 组织机构名称
     */
    @TableField(value = "org_name")
    private String orgName;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 状态,1启用0无效
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 菜单的层级
     */
    @TableField(value = "level")
    private Integer level;

}
