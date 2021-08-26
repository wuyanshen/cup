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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.dto.RoleDTO;
import com.lvcoding.log.CupLog;
import com.lvcoding.service.SysRoleService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表(SysRole)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@RestController
@RequestMapping("roles")
@AllArgsConstructor
public class SysRoleController {


    private final SysRoleService sysRoleService;


    /**
     * 获取所有的角色列表
     *
     * @param
     * @return com.lvcoding.util.Res
     */
    @GetMapping("list")
    public Res list(){
        return Res.success(this.sysRoleService.list());
    }

    /**
     * 分页查询角色
     *
     * @param page
     * @param sysRole
     * @return com.lvcoding.util.Res
     */
    @CupLog(type = "3",value = "查询角色")
    @PreAuthorize("@pm.hasPermission('sys:role:view')")
    @GetMapping("page")
    public Res page(Page page,SysRole sysRole){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(sysRole.getRoleName())){
            queryWrapper.lambda().like(SysRole::getRoleName,sysRole.getRoleName());
        }
        if(!StringUtils.isEmpty(sysRole.getRoleCode())){
            queryWrapper.lambda().like(SysRole::getRoleCode,sysRole.getRoleCode());
        }
        return Res.success(sysRoleService.page(page,queryWrapper));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:role:delete')")
    @CupLog(type = "3",value = "删除角色")
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id") Integer id){
        return Res.success(sysRoleService.delete(id));
    }

    /**
     * 更新角色
     *
     * @param sysRole
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:role:update')")
    @CupLog(type = "3",value = "更新角色")
    @PutMapping
    public Res update(@RequestBody SysRole sysRole){
        return Res.success(this.sysRoleService.updateById(sysRole));
    }

    /**
     * 新增角色
     *
     * @param sysRole
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:role:add')")
    @CupLog(type = "3",value = "新增角色")
    @PostMapping
    public Res add(@RequestBody SysRole sysRole){
        return Res.success(this.sysRoleService.save(sysRole));
    }

    /**
     * 通过角色id查询菜单id集合
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @GetMapping("menuIds/{id}")
    public Res findMenuIds(@PathVariable("id") Integer id){
        return Res.success(this.sysRoleService.findMenuIds(id));
    }


    /**
     * 保存权限
     *
     * @param roleDTO
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:permission:update')")
    @PutMapping("permission")
    public Res updatePermission(@RequestBody RoleDTO roleDTO){
        return Res.success(this.sysRoleService.saveRoleMenu(roleDTO));
    }

}
