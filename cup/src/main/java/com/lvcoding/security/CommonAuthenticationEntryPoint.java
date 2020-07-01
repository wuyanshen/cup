package com.lvcoding.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-25 1:34 下午
 * @discription 自定义匿名访问(也就是没登录就访问)资源的提示
 */
@Component
public class CommonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        int code = HttpStatus.UNAUTHORIZED.value();
        response.getWriter().write(objectMapper.writeValueAsString(Res.fail(code, "您无权访问 "+ request.getRequestURI())));
    }
}
