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
import java.util.Objects;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:14 下午
 * @discription 系统日志工具类
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
        sysLog.setIp(ServletUtil.getClientIP(request));
        //通过hutool将参数转换成用`&`拼接的url参数
        sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
        sysLog.setUpdateTime(LocalDateTime.now());
        sysLog.setCreateTime(LocalDateTime.now());
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
