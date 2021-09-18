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

package com.lvcoding.datascope.nouse;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.SysUser;
import com.lvcoding.security.CommonUser;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @description 获取数据权限范围
 * @date 2021-08-24 下午6:40
 */
public class OriginalDataScopeHandler implements DataScopeHandler {

    @Autowired
    @Lazy
    private SysOrgService sysOrgService;

    @Override
    public boolean getDataScope(List<String> orgIds) {
        CommonUser user = SecurityUtil.getUser();
        SysUser sysUser = user.getSysUser();
        List<SysRole> sysRoles = user.getRoles();
        SysRole role = sysRoles.stream().min(Comparator.comparing(sysRole -> Integer.parseInt(sysRole.getScopeType().trim()))).get();
        String scopeType = role.getScopeType();
        String dataScope = role.getDataScope();


        switch (scopeType) {
            // 查询所有
            case CommonConstant.DATA_SCOPE_ALL:
                return true;

            // 自定义
            case CommonConstant.DATA_SCOPE_CUSTOMIZE:
                List<String> collect = Arrays.stream(dataScope.split(",")).collect(Collectors.toList());
                orgIds.addAll(collect);
                break;

            // 本部门及子部门
            case CommonConstant.DATA_SCOPE_DEPARTMENT:
                Integer orgId = sysUser.getOrgId();
                List<SysOrg> sysOrgList = sysOrgService.list(Wrappers.<SysOrg>lambdaQuery().eq(SysOrg::getPid, orgId));
                List<String> childOrgList = null;
                if(ObjectUtil.isNotEmpty(sysOrgList)) {
                    childOrgList = sysOrgList.stream().map(sysOrg -> sysOrg.getId() + "").collect(Collectors.toList());
                }
                orgIds.add(orgId + "");
                if(ObjectUtil.isNotEmpty(childOrgList)) {
                    orgIds.addAll(childOrgList);
                }
                break;

            // 本部门
            case CommonConstant.DATA_SCOPE_SELF_DEPARTMENT:
                orgIds.add(sysUser.getOrgId() + "");
                break;

            // 仅自己
            default:
                orgIds.add("SELF");
                orgIds.add(sysUser.getUsername());
                break;
        }

        return false;
    }
}
