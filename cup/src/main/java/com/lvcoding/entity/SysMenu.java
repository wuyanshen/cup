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

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表(SysMenu)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父id
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 所有的父id
     */
    @TableField(value = "pids")
    private String pids;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 权限标识
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 菜单类型(0菜单1按钮)
     */
    @TableField(value = "type")
    private String type;

    /**
     * VUE页面
     */
    @TableField(value = "component")
    private String component;

    /**
     * 路由缓冲(1开启0关闭)
     */
    @TableField(value = "keep_alive")
    private String keepAlive;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 状态,1可用0不可用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否展示,1展示 0隐藏
     */
    @TableField(value = "is_show")
    private Integer isShow;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;


    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
