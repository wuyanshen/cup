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

package com.lvcoding.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.lvcoding.entity.dto.TreeNode;

/**
 * @author wuyanshen
 * @date 2020-03-29 4:29 下午
 * @description 菜单VO
 */
@Data
public class MenuVO extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private int id;
    /**
     * 父id
     */
    private int menuPid;

    /**
     * 所有的父id
     */
    private String menuPids;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String url;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型(0菜单1按钮)
     */
    private String type;

    /**
     * VUE页面
     */
    private String component;

    /**
     * 路由缓冲(1开启0关闭)
     */
    private String keepAlive;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 状态,1可用0不可用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除,1已删0未删
     */
    private Integer delFlag;

    @Override
    public boolean equals(Object o) {
        if (o instanceof MenuVO) {
            return ((MenuVO) o).getId() == id;
        }
        return super.equals(o);
    }

    /**
     * 重新equals方法就必须重写hashcode方法
     * 因为hashcode协定规定：声明相等对象必须具有相等的哈希码
     * <p>
     * 如果不重写，那么对象equals的方法相等了，但是他们的hashcode
     * 不相等，与这个协定违背
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
