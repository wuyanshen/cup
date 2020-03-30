package com.cup.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuyanshen
 * @date 2020-03-24 2:52 下午
 * @discription 自定义动态判断权限
 */
@Component
public class CommonHasPermission {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermision(HttpServletRequest request, Authentication authentication) {
        if (authentication.getPrincipal() instanceof CommonUser) {
            CommonUser commonUser = (CommonUser) authentication.getPrincipal();
            return commonUser.getAuthorities().stream().anyMatch(permission -> antPathMatcher.match(permission.getAuthority(), request.getRequestURI()));
        }
        return false;
    }
}
