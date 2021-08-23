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

package com.lvcoding.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 机构树实体类
 */
@Data
public class OrgTree extends TreeNode implements Serializable {
    private static final long serialVersionUID = 772244235379764292L;

    /**
     * 是否叶子节点
     */
    private Integer isLeaf;
    /**
     * 组织机构名称
     */
    private String orgName;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态,1启用0无效
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单的层级
     */
    private Integer level;

}
