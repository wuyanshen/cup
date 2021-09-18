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

package com.lvcoding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvcoding.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过角色名称list查询菜单权限
     *
     * @param permissions
     * @return java.util.List<java.lang.String>
     */
    List<SysMenu> loadPermissionByRoleCode(@Param("permissions") List<String> permissions);

    /**
     * 通过菜单id删除角色菜单关联表
     *
     * @param menuId
     * @return int
     */
    int deleteMenuRoleById(Integer menuId);

    /**
     * 根据角色id集合获取用户菜单
     *
     * @param roleIds
     * @return List<SysMenu>
     */
    List<SysMenu> findMenuByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
