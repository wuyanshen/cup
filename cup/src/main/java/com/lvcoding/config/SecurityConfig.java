package com.lvcoding.config;

import com.lvcoding.security.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @discription SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CommonLoginSuccessHandler commonLoginSuccessHandler;

    private final CommonLoginFailureHandler commonLoginFailureHandler;

    private final CommonUserDetailServiceImpl commonUserDetailServiceImpl;

    private final CommonAccessDeniedHandler commonAccessDeniedHandler;

    private final CommonLogoutSuccessHandler commonLogoutSuccessHandler;

    private final CommonAuthenticationEntryPoint commonAuthenticationEntryPoint;

    private final TokenFilter tokenFilter;


    @Override
    public void configure(WebSecurity web) {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/favicon.ico", "/assets/**", "/images/**", "/css/**", "/js/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessHandler(commonLogoutSuccessHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/certification")
                .successHandler(commonLoginSuccessHandler)
                .failureHandler(commonLoginFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers( "/druid/**", "/token/check", "/certification", "/websocket/**", "/tenant/list").permitAll()
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

}
