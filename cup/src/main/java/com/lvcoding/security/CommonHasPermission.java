package com.lvcoding.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author wuyanshen
 * @date 2020-03-24 2:52 下午
 * @discription 自定义动态判断权限
 */
@Component("pm")
public class CommonHasPermission {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    public boolean hasPermission(String permission){
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }
}
