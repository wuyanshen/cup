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

package com.lvcoding.controller;

import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.dto.MenuTree;
import com.lvcoding.log.CupLog;
import com.lvcoding.security.CommonUser;
import com.lvcoding.security.TokenService;
import com.lvcoding.service.SysMenuService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@RestController
@RequestMapping("menus")
@AllArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    private final TokenService tokenService;


    /**
     * 查询所有菜单列表
     *
     * @param sysMenu
     * @return Res
     * @author wuyanshen
     */
    @GetMapping("list")
    public Res list(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.findMenuList(sysMenu);
        return Res.success(list);
    }

    /**
     * 获取当前用户的菜单树
     *
     * @param request
     * @return Res
     */
    @GetMapping("tree")
    public Res menuTree(HttpServletRequest request) {
        CommonUser commonUser = tokenService.getCommonUser(request);
        List<Integer> roleIds = commonUser.getRoles().stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
        List<MenuTree> list = sysMenuService.findMenuByRoleIds(roleIds);
        return Res.success(list);
    }

    /**
     * 查询菜单
     *
     * @return Res
     */
    @PreAuthorize("@pm.hasPermission('sys:menu:view')")
    @CupLog(type = "2",value = "查询菜单")
    @GetMapping("treePage")
    public Res menuTreePage(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.findAllMenuTree(sysMenu);
        return Res.success(list);
    }

    /**
     * 新增菜单
     *
     * @param
     * @return MenuTree
     */
    @PreAuthorize("@pm.hasPermission('sys:menu:add')")
    @CupLog(type = "2",value = "新增菜单")
    @PostMapping
    public Res add(@RequestBody SysMenu sysMenu){
        return Res.success(sysMenuService.addMenu(sysMenu));
    }

    /**
     * 修改菜单
     *
     * @param sysMenu
     * @return Res
     */
    @PreAuthorize("@pm.hasPermission('sys:menu:update')")
    @CupLog(type = "2",value = "修改菜单")
    @PutMapping
    public Res update(@RequestBody SysMenu sysMenu){
        return Res.success(sysMenuService.updateById(sysMenu));
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:menu:delete')")
    @CupLog(type = "2",value = "删除菜单")
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id")Integer id){
        return Res.success(sysMenuService.deleteById(id));
    }
}
