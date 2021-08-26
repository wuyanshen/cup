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

import com.lvcoding.constant.CommonConstant;
import com.lvcoding.exception.ImgCodeException;
import com.lvcoding.util.Res;
import com.lvcoding.util.ResUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyanshen
 * @date 2020-03-23 4:16 下午
 * @description 自定义登录失败的处理
 */
@Slf4j
@Component
public class CommonLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (loginType.equalsIgnoreCase(CommonConstant.LOGIN_TYPE_JSON)) {

            Res res = new Res();
            res.setCode(1);
            res.setData("");
            if (exception instanceof DisabledException) {
                res.setCode(433);
                res.setMsg("账户已被禁用，请联系管理员");
            } else if (exception instanceof BadCredentialsException) {
                res.setCode(420);
                res.setMsg("用户名或密码错误");
            } else if (exception instanceof UsernameNotFoundException) {
                res.setMsg("用户名不存在");
            } else if (exception instanceof ImgCodeException) {
                res.setCode(-1);
                res.setMsg("验证码错误");
            } else {
                res.setCode(500);
                res.setMsg("系统错误，请联系管理员");
            }

            ResUtil.jsonResult(response, res);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
