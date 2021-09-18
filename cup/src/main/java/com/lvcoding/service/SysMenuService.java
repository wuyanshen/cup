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
import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.dto.MenuTree;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据角色id获取用户菜单
     *
     * @param roleIds
     * @return List<SysMenu>
     */
    List<MenuTree> findMenuByRoleIds(List<Integer> roleIds);

    boolean addMenu(SysMenu sysMenu);

    List<SysMenu> findAllMenuTree(SysMenu sysMenu);

    List<SysMenu> findMenuList(SysMenu sysMenu);
}
