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

package com.lvcoding.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author wuyanshen
 * @date 2020-03-29 4:09 下午
 * @description 菜单树页面传值VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
