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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色数据传输类
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = -91881003056749924L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色英文名称
     */
    private String roleCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态,1可用0不可用
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除,1已删0未删
     */
    private Integer delFlag;

    private List<Integer> menuIds;

}
