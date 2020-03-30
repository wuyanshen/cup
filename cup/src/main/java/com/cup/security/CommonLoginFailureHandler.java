package com.cup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cup.constant.CommonConstant;
import com.cup.util.Res;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyanshen
 * @date 2020-03-23 4:16 下午
 * @discription 自定义登录失败的处理
 */
@Slf4j
@Component
public class CommonLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.security.loginType}")
    private String loginType;

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (loginType.equalsIgnoreCase(CommonConstant.LOGIN_TYPE_JSON)) {
            response.setContentType("application/json;charset=UTF-8");

            Res res = new Res();
            res.setCode(1);
            res.setData("");
            if (exception instanceof DisabledException) {
                res.setCode(433);
                res.setMsg("账户已被禁用，请联系管理员");
            } else if (exception instanceof BadCredentialsException) {
                res.setCode(420);
                res.setMsg("用户名或密码错误");
            } else if (exception instanceof UsernameNotFoundException) {
                res.setMsg("用户名不存在");
            } else {
                res.setCode(500);
                res.setMsg("系统错误，请联系管理员");
            }

            response.getWriter().write(objectMapper.writeValueAsString(res));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
