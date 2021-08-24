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

package com.lvcoding.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysUser;
import com.lvcoding.entity.dto.UserDTO;
import com.lvcoding.entity.vo.UserVO;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 更新密码
     *
     * @param sysUser
     * @return boolean
     */
    boolean updatePwd(UserVO sysUser);

    /**
     * 通过用户id查询角色id集
     *
     * @param id
     * @return java.lang.String
     */
    List<Integer> getRoleIdsByUserId(Integer id);

    /**
     * 更新用户
     *
     * @param userDTO
     * @return boolean
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 保存用户
     *
     * @param userDTO
     * @return boolean
     */
    boolean saveUser(UserDTO userDTO);

    /**
     * 获取当前用户的上级
     *
     * @param username
     * @return List<SysUser>
     */
    List<SysUser> findSuperior(String username);

    /**
     * 获取用户分页
     *
     * @param page
     * @param sysUser
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     */
    IPage getPage(Page page, SysUser sysUser);
}
