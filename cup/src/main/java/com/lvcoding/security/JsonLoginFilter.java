package com.lvcoding.security;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.SysUser;
import com.lvcoding.exception.ImgCodeException;
import com.lvcoding.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyanshen
 * @discription json登录过滤器
 * @date 2021-05-05 下午11:36
 */
@Component
public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public JsonLoginFilter(CommonLoginSuccessHandler loginSuccessHandler, CommonLoginFailureHandler loginFailHandler, @Lazy AuthenticationManager authenticationManager) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/certification", HttpMethod.POST.name()));
        setAuthenticationSuccessHandler(loginSuccessHandler);
        setAuthenticationFailureHandler(loginFailHandler);
        setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 判断是否是json格式登录
        if (request.getContentType().equals(CommonConstant.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (ServletInputStream inputStream = request.getInputStream()) {
                SysUser user = objectMapper.readValue(inputStream, SysUser.class);

                // 校验图片验证码
                String browserDetails = request.getHeader("User-Agent");
                if (!browserDetails.contains("Postman")) {
                    String imgCode = user.getCaptcha();
                    String redisImgCode = RedisUtil.get(CommonConstant.CAPTCHA_CODE_KEY + user.getUuid());
                    if (StrUtil.isBlank(imgCode) || StrUtil.isBlank(redisImgCode) || !imgCode.equals(redisImgCode)) {
                        RedisUtil.del(CommonConstant.CAPTCHA_CODE_KEY + user.getUuid());
                        throw new ImgCodeException("验证码错误");
                    }
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                this.setDetails(request, authenticationToken);
                return this.getAuthenticationManager().authenticate(authenticationToken);
            }
            // form表单格式登录
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

}
