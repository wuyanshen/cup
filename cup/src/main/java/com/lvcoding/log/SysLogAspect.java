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

package com.lvcoding.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.util.SpringContextHolder;
import com.lvcoding.util.SysLogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author wuyanshen
 * @date 2020-04-23 5:20 下午
 * @description 系统日志切面类
 */
@Aspect
@Slf4j
public class SysLogAspect {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        com.lvcoding.entity.SysLog logVo = SysLogUtil.getSysLog();
        logVo.setTitle(sysLog.value());
        logVo.setType(sysLog.type());
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();

        // 接口响应数据
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        logVo.setTime(endTime - startTime);
        logVo.setResponse(objectMapper.writeValueAsString(obj));

        // 如果是get请求就不记录日志
        if (!logVo.getMethod().equalsIgnoreCase(CommonConstant.HTTP_METHOD_GET)) {
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }

        return obj;
    }

}

