package com.lvcoding.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.util.DateUtil;
import com.lvcoding.util.JwtUtil;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-23 1:47 下午
 * @discription 自定义登录成功后的处理
 */

@Slf4j
@Component
public class CommonLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.security.loginType}")
    private String loginType;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        log.info("=== 用户【{}】在 {} 登录了系统 ===", username, DateUtil.nowString());

        if (loginType.equalsIgnoreCase(CommonConstant.LOGIN_TYPE_JSON)) {
            //生成token
            String token = JwtUtil.createToken(authentication);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Res.success(0, "登录成功", token)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
