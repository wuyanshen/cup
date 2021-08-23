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

package com.lvcoding.exception;

import com.lvcoding.util.ExceptionUtil;
import com.lvcoding.util.Res;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wuyanshen
 * @date 2020-03-21 7:03 下午
 * @description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Res errorHandler(Exception ex) {
        //判断异常的类型,返回不一样的返回值
        if (ex instanceof ExpiredJwtException) {
            return Res.fail(401, "token过期了,请重新登录");
        }
        else if (ex instanceof AccessDeniedException) {
            return Res.fail(403, "您无权访问该资源");
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            return Res.fail("请求参数错误(参数类型或参数个数不正确)");
        } else {
            // 将报错信息打印出来
            log.error(ExceptionUtil.getMessage(ex));
            // 将报错信息返回给客户端
            return Res.fail(500, "系统异常，请联系管理员");
        }
    }
}
