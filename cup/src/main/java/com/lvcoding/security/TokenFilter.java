package com.lvcoding.security;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.common.CupProperties;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyanshen
 * @date 2020-03-25 2:18 下午
 * @discription 校验token过滤器
 * OncePerRequestFilter 可以保证一个请求只执行一次TokenFilter过滤器
 */
@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CupProperties cupProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 直接放过不需要检查token的请求
        if (cupProperties.getTokenIgnoreUrl().contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 演示模式
        this.demoMode(request, response);

        CommonUser commonUser = tokenService.getCommonUser(request);
        if (ObjectUtil.isNotEmpty(commonUser)) {
            tokenService.validateToken(commonUser);
            //将认证信息放到SpringSecurity上下文中，给后续的SpringSecurity鉴权使用，如果不放，SpringSecurity就不能鉴权
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(commonUser, null,
                    commonUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        //继续走其它过滤器
        filterChain.doFilter(request, response);
    }

    /**
     * 演示模式
     *
     * @param request
     * @param response
     * @author wuyanshen
     */
    private void demoMode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (cupProperties.isEnableDemo()) {
            List<String> methods = new ArrayList<>();
            methods.add("DELETE");
            methods.add("POST");
            methods.add("PUT");
            if (methods.contains(request.getMethod())) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(objectMapper.writeValueAsString(Res.fail("演示模式，不允许操作")));
                return;
            }
        }
    }
}
