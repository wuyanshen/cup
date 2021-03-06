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

package com.lvcoding.security;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import com.lvcoding.util.DateUtil;
import com.lvcoding.util.Res;
import com.lvcoding.util.ResUtil;
import com.lvcoding.util.SysLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-24 10:53 下午
 * @description 自定义退出成功后执行
 */
@Slf4j
@Component
public class CommonLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CommonUser commonUser = tokenService.getCommonUser(request);
        if (ObjectUtil.isNotEmpty(commonUser)) {
            // 记录日志
            addLog(commonUser.getUsername());
            // 删除redis中的token
            tokenService.deleteToken(commonUser.getToken());
            log.info("=== 用户【{}】在 {} 退出了系统 ===", commonUser.getUsername(), DateUtil.nowString());
        }
        ResUtil.jsonResult(response, Res.success(0, "退出成功"));
    }

    /**
     * 记录日志
     */
    private void addLog(String username) {
        SysLog sysLog = SysLogUtil.getSysLog();
        sysLog.setTitle("【"+username + "】登出");
        sysLog.setType("1");
        sysLog.setCreateBy(username);
        sysLogService.save(sysLog);
    }
}
