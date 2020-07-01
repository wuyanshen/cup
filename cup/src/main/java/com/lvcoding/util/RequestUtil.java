package com.lvcoding.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * request 工具类
 * @author wuyanshen
 */
@UtilityClass
public class RequestUtil {

    public HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    public ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}
