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

package com.lvcoding.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.datascope.DataScope;
import com.lvcoding.entity.SysUser;
import com.lvcoding.entity.dto.UserDTO;
import com.lvcoding.entity.vo.UserVO;
import com.lvcoding.log.CupLog;
import com.lvcoding.security.CommonUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.Res;
import com.lvcoding.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户表(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 查询用户信息
     *
     * @param authentication
     * @return String
     */
    @GetMapping("info")
    public Res info(Authentication authentication) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, Objects.requireNonNull(SecurityUtil.getUser()).getUsername()));
        return Res.success(sysUser);
    }


    /**
     * 分页查询用户信息
     *
     * @param page
     * @return Res
     */
    @DataScope
    @CupLog(value = "查询用户列表", type = "1")
    @PreAuthorize("@pm.hasPermission('sys:user:view')")
    @GetMapping("page")
    public Res page(Page page, SysUser sysUser) {
        return Res.success(this.sysUserService.getPage(page, sysUser));
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return com.longyi.util.Res
     */
    @CupLog(value = "新增用户", type = "1")
    @PreAuthorize("@pm.hasPermission('sys:user:add')")
    @PostMapping
    public Res create(@RequestBody UserDTO userDTO) {
        return Res.success(this.sysUserService.saveUser(userDTO));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return com.longyi.util.Res
     */
    @CupLog(value = "删除用户", type = "1")
    @PreAuthorize("@pm.hasPermission('sys:user:delete')")
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id") Integer id) {
        return Res.success(this.sysUserService.removeById(id));
    }

    /**
     * 更新用户
     *
     * @param userDTO
     * @return com.longyi.util.Res
     */
    @CupLog(value = "更新用户", type = "1")
    @PreAuthorize("@pm.hasPermission('sys:user:update')")
    @PutMapping
    public Res update(@RequestBody UserDTO userDTO) {
        return Res.success(this.sysUserService.updateUser(userDTO));
    }


    /**
     * 更新密码
     *
     * @param userVO
     * @return
     */
    @CupLog(value = "更新密码", type = "1")
    @PutMapping("pwd")
    public Res updatePwd(@RequestBody UserVO userVO) {
        return Res.success(this.sysUserService.updatePwd(userVO));
    }

    /**
     * 校验原密码是否正确
     *
     * @param password
     * @return
     */
    @GetMapping("pwd/check")
    public Res checkPwd(@RequestParam("password") String password) {
        CommonUser user = (CommonUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return Res.success("密码匹配");
        } else {
            return Res.fail("密码不匹配");
        }
    }

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    @GetMapping("name/check")
    public Res checkUsername(@RequestParam("username") String username) {
        boolean flag = this.sysUserService.list().stream().anyMatch(user -> user.getUsername().equals(username));
        if (flag) {
            return Res.fail("用户名已存在");
        } else {
            return Res.success("用户名可用");
        }
    }

    /**
     * 查询用户的角色的id集合
     *
     * @param id
     * @return
     */
    @GetMapping("roleIds/{id}")
    public Res roleIds(@PathVariable("id")Integer id) {
        return Res.success(this.sysUserService.getRoleIdsByUserId(id));
    }


    /**
     * 重置密码
     *
     * @param userVO
     * @return
     */
    @CupLog(value = "重置密码", type = "1")
    @PutMapping("resetPwd")
    public Res resetPwd(@RequestBody UserVO userVO) {
        return Res.success(this.sysUserService.resetPwd(userVO));
    }

}
