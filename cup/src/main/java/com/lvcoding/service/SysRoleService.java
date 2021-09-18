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

package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.dto.RoleDTO;

import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 删除角色
     *
     * @param id
     * @return boolean
     */
    boolean delete(Integer id);

    /**
     * 通过角色id查询菜单id集合
     *
     * @param id
     * @return java.util.List<java.lang.Integer>
     */
    List<Integer> findMenuIds(Integer id);

    boolean saveRoleMenu(RoleDTO roleDTO);
}
