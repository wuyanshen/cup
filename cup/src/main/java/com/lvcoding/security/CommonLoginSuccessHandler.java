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

package com.lvcoding.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import com.lvcoding.util.DateUtil;
import com.lvcoding.util.Res;
import com.lvcoding.util.SysLogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-23 1:47 下午
 * @description 自定义登录成功后的处理
 */

@Slf4j
@Component
public class CommonLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Value("${spring.security.loginType}")
    private String loginType;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLogService sysLogService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        log.info("=== 用户【{}】在 {} 登录了系统 ===", username, DateUtil.nowString());

        if (loginType.equalsIgnoreCase(CommonConstant.LOGIN_TYPE_JSON)) {
            // 记录日志
            addLog();
            // 生成token
            CommonUser commonUser = (CommonUser) authentication.getPrincipal();
            String token = tokenService.createToken(commonUser);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Res.success(0, "登录成功", token)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    /**
     * 记录日志
     */
    private void addLog() {
        SysLog sysLog = SysLogUtils.getSysLog();
        sysLog.setTitle(SysLogUtils.getUserName() + "用户登录");
        sysLog.setType("1");
        sysLogService.save(sysLog);
    }
}
