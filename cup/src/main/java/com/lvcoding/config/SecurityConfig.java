/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.config;

import com.lvcoding.security.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author wuyanshen
 * @date 2020-03-19 11:42 上午
 * @description SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CommonUserDetailServiceImpl commonUserDetailServiceImpl;

    private final CommonAccessDeniedHandler commonAccessDeniedHandler;

    private final CommonLogoutSuccessHandler commonLogoutSuccessHandler;

    private final CommonAuthenticationEntryPoint commonAuthenticationEntryPoint;

    private final TokenFilter tokenFilter;

    private final JsonLoginFilter jsonLoginFilter;


    @Override
    public void configure(WebSecurity web) {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/diagram-viewer/**","/editor-app/**","/modeler.html","/druid/**","/favicon.ico", "/assets/**", "/images/**", "/css/**", "/js/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 登录前，先拦截token
        http.addFilterBefore(tokenFilter, JsonLoginFilter.class);
        http.addFilterAt(jsonLoginFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf().disable()
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessHandler(commonLogoutSuccessHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/certification")
                .and()
                .authorizeRequests()
                // 登录、验证码允许匿名访问
                .antMatchers("/certification", "/captcha/captchaImage").anonymous()
                .antMatchers( "/service/**", "/token/check", "/websocket/**", "/tenant/list", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(commonAccessDeniedHandler)
                .authenticationEntryPoint(commonAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(commonUserDetailServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 密码加密器
     *
     * @param
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
