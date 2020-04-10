package com.lvcoding.security;

import com.lvcoding.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyanshen
 * @date 2020-03-24 10:53 下午
 * @discription 自定义退出成功后执行
 */
@Slf4j
@Component
public class CommonLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("=== 用户【{}】在 {} 退出了系统 ===", authentication.getName(), DateUtil.nowString());
        response.sendRedirect("/login.html");
    }
}
