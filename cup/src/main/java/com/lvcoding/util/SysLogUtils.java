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

package com.lvcoding.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.lvcoding.entity.SysLog;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:14 下午
 * @description 系统日志工具类
 */
@UtilityClass
public class SysLogUtils {

    /**
     * 组装系统日志类
     *
     * @param
     * @return com.lvcoding.entity.SysLog
     */
    public SysLog getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setMethod(request.getMethod());
        sysLog.setCreateBy(getUserName());
        sysLog.setRequestUri(request.getRequestURI());
        //通过hutool获取ip
        String ip = ServletUtil.getClientIP(request);
        sysLog.setIp(ip);
        sysLog.setAddr(AddressUtil.getAddressByIp(ip));
        //通过hutool将参数转换成用`&`拼接的url参数
        sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
        sysLog.setUpdateTime(new Date());
        sysLog.setCreateTime(new Date());
        return sysLog;
    }

    /**
     * 获取当前登录用户的用户名
     *
     * @param
     * @return java.lang.String
     */
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            return null;
        }
        return authentication.getName();
    }
}
