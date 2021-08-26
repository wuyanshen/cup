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

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.SysLog;
import com.lvcoding.util.SpringContextHolder;
import com.lvcoding.util.SysLogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyanshen
 * @date 2020-04-23 5:20 下午
 * @description 系统日志切面类
 */
@Aspect
@Slf4j
public class SysLogAspect {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(cupLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, CupLog cupLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        SysLog sysLog = SysLogUtil.getSysLog();
        sysLog.setTitle(cupLog.value());
        sysLog.setType(cupLog.type());
        // 设置请求参数
        this.handleRequestParams(point, sysLog);

        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();

        // 接口响应数据
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        sysLog.setTime(endTime - startTime);
        sysLog.setResponse(objectMapper.writeValueAsString(obj));

        // 如果是get请求就不记录日志
        if (!sysLog.getMethod().equalsIgnoreCase(CommonConstant.HTTP_METHOD_GET)) {
            SpringContextHolder.publishEvent(new SysLogEvent(sysLog));
        }

        return obj;
    }

    private void handleRequestParams(ProceedingJoinPoint point, SysLog sysLog) {
        Object[] args = point.getArgs();
        String params = this.argsArrayToString(args);
        sysLog.setParams(params);
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    params.append(jsonObj.toString()).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}

