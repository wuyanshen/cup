package com.lvcoding.security;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.util.DateUtil;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CommonUser commonUser = tokenService.getCommonUser(request);
        if(ObjectUtil.isNotEmpty(commonUser)){
            tokenService.deleteToken(commonUser.getToken());
            log.info("=== 用户【{}】在 {} 退出了系统 ===", commonUser.getUsername(), DateUtil.nowString());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Res.success(0, "退出成功")));
    }
}
