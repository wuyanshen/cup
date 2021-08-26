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

package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.datascope.DataScope;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.entity.SysUser;
import com.lvcoding.dao.SysUserMapper;
import com.lvcoding.entity.dto.UserDTO;
import com.lvcoding.entity.vo.UserVO;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final SysOrgService sysOrgService;


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean updatePwd(UserVO userVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userVO, sysUser);
        sysUser.setPassword(this.passwordEncoder.encode(userVO.getPassword()));
        return this.baseMapper.updatePwd(sysUser);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer id) {
        return this.baseMapper.getRolesByUserId(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDTO userDTO) {
        //更新用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        if (!StringUtils.isEmpty(sysUser.getPassword())) {
            sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        }
        boolean flag1 = this.baseMapper.updateById(sysUser) > 0;

        //删除用户角色关联表
        boolean flag2 = this.baseMapper.deleteUserRole(sysUser.getId());

        //新增用户角色关联表
        List<Boolean> collect = userDTO.getRoleIds().stream().map(roleId -> {
            return this.baseMapper.saveUserRole(sysUser.getId(), roleId);
        }).collect(Collectors.toList());

        return flag1 && flag2;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean saveUser(UserDTO userDTO) {
        // 新增用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        sysUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        boolean flag1 = this.baseMapper.insert(sysUser) > 0;

        //新增角色关联
        userDTO.getRoleIds().stream().peek(roleId -> {
            this.baseMapper.saveUserRole(sysUser.getId(), roleId);
        }).collect(Collectors.toList());

        return flag1;
    }

    @Override
    public List<SysUser> findSuperior(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        Integer orgId = sysUser.getOrgId();
        if (orgId != null) {
            SysOrg sysOrg = sysOrgService.getById(orgId);
            Integer orgPid = sysOrg.getOrgPid();
            return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getOrgId, orgPid));
        }
        return null;
    }

    @Override
    public IPage getPage(Page page, SysUser sysUser) {
        return this.baseMapper.getPageScope(page, sysUser, new DataScope());
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean resetPwd(UserVO userVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userVO, sysUser);
        if (!StringUtils.isEmpty(sysUser.getPassword())) {
            sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        }
        return this.baseMapper.updateById(sysUser) > 0;
    }
}
