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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyanshen
 * @date 2020-06-05 4:50 下午
 * @description 日志控制器
 */
@RestController
@RequestMapping("logs")
@AllArgsConstructor
public class SysLogController {

    private final SysLogService sysLogService;

    /**
     * 分页查询日志
     *
     * @param page
     * @param sysLog
     * @return Res
     */
    @PreAuthorize("@pm.hasPermission('sys:log:view')")
    @RequestMapping("page")
    public Res page(Page page, SysLog sysLog) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sysLog.getTitle())) {
            queryWrapper.lambda().like(SysLog::getTitle, sysLog.getTitle());
        }
        if (!StringUtils.isEmpty(sysLog.getType())) {
            queryWrapper.lambda().eq(SysLog::getType, sysLog.getType());
        }
        queryWrapper.lambda().orderByDesc(SysLog::getCreateTime);
        return Res.success(sysLogService.page(page, queryWrapper));
    }

    /**
     * 清空日志
     */
    @PreAuthorize("@pm.hasPermission('sys:log:delete')")
    @DeleteMapping("delete")
    public Res delete() {
        return Res.success(sysLogService.remove(Wrappers.emptyWrapper()));
    }
}
