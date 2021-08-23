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

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.security.CommonUser;
import com.lvcoding.security.TokenService;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuyanshen
 * @date 2020-03-25 5:18 下午
 * @description token相关控制器
 */
@RequestMapping("token")
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 校验token是否有效
     *
     */
    @GetMapping("/check")
    public Res refreshToken(HttpServletRequest request) {
        CommonUser commonUser = tokenService.getCommonUser(request);
        if (ObjectUtil.isEmpty(commonUser)) {
            return Res.fail(401,"token无效或不存在");
        }
        return Res.success("token有效");
    }
}
