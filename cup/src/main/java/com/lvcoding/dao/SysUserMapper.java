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

package com.lvcoding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return ysUser
     */
    SysUser loadUserByUsername(String username);

    /**
     * 分页查询
     *
     * @param page
     * @param sysUser
     * @return IPage<SysUser>
     */
    IPage<SysUser> selectPageVo(Page<?> page, @Param("sysUser") SysUser sysUser);

    /**
     *  更新密码
     * @param sysUser
     * @return
     */
    boolean updatePwd(SysUser sysUser);

    List<Integer> getRolesByUserId(Integer id);

    boolean deleteUserRole(Integer id);

    boolean saveUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 分页查询用户
     *
     * @param page
     * @param sysUser
     * @param dataScope
     * @return IPage
     */
    IPage<SysUser> getPageScope(Page page, @Param("sysUser") SysUser sysUser);
}
