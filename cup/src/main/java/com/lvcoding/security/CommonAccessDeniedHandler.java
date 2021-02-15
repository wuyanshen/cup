package com.lvcoding.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-24 4:59 下午
 * @discription 当登录后访问的接口没有权限时，自定义返回结果
 */
@Component
public class CommonAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Res.success(403, "您无权访问该资源，请联系管理员")));
    }
}
