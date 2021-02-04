package com.lvcoding.util;

import com.lvcoding.security.CommonUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SpringSecurity工具类
 * @author wuyanshen
 */
@Slf4j
@UtilityClass
public class SecurityUtil {

    /**
     * 获取当前登录用户
     */
    public CommonUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return principal instanceof CommonUser ? ((CommonUser) principal) : null;
    }
}
