package com.cup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cup.constant.CommonConstant;
import com.cup.util.DateUtil;
import com.cup.util.JwtUtil;
import com.cup.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
    private CommonUserDetailServiceImpl commonUserDetailServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //检查token或者刷新token直接放过
        if (request.getRequestURI().equals(CommonConstant.CHECK_TOKEN_URI)||request.getRequestURI().equals(CommonConstant.REFRESH_TOKEN_URI)) {
            filterChain.doFilter(request, response);
            return;
        }

        //如果是登录请求直接放过
        if (request.getRequestURI().equals(CommonConstant.LOGIN_URI) && request.getMethod().equalsIgnoreCase(CommonConstant.POST)) {
            filterChain.doFilter(request, response);
            //这里必须使用return，否则登录请求(/login)还继续会往下走
            return;
        }

        response.setContentType("application/json;charset=utf-8");

        //判断token是否为空
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            response.getWriter().write(objectMapper.writeValueAsString(Res.fail("请求中必须携带token")));
            return;
        }

        //验证token正确性
        if (!JwtUtil.validateToken(token)) {
            log.info("token校验失败 {}", DateUtil.nowString());
            response.getWriter().write(objectMapper.writeValueAsString(Res.fail(401, "无效的token,请重新登录")));
            return;
        }

        //解析token
        Map<String, Object> stringObjectMap = JwtUtil.parseToken(token);
        String username = stringObjectMap.get("username").toString();
        UserDetails userDetails = commonUserDetailServiceImpl.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(userDetails)) {
            response.getWriter().write(objectMapper.writeValueAsString(Res.fail("token中的用户不存在")));
            return;
        }

        //将认证信息放到SpringSecurity上下文中
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("token校验通过 {}", DateUtil.nowString());

        //继续走其它过滤器
        filterChain.doFilter(request, response);
    }
}
