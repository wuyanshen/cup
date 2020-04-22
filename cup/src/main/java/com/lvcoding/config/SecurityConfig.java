package com.lvcoding.config;

import com.lvcoding.security.*;
import com.lvcoding.session.CommonExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 * @author wuyanshen
 * @date 2020-03-19 11:42 上午
 * @discription SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CommonExpiredSessionStrategy commonExpiredSessionStrategy;

    @Autowired
    private CommonLoginSuccessHandler commonLoginSuccessHandler;

    @Autowired
    private CommonLoginFailureHandler commonLoginFailureHandler;

    @Autowired
    private CommonUserDetailServiceImpl commonUserDetailServiceImpl;

    @Autowired
    private CommonAccessDeniedHandler commonAccessDeniedHandler;

    @Autowired
    private CommonLogoutSuccessHandler commonLogoutSuccessHandler;

    @Autowired
    private CommonAuthenticationEntryPoint commonAuthenticationEntryPoint;

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
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
                        .deleteCookies("JSESSIONID")
                .and()
                    .rememberMe()
                    .rememberMeCookieName("remember-me-cookie")
                    .rememberMeParameter("remember-me-new")
                    .tokenValiditySeconds(2 * 24 * 60 * 60)
                    .tokenRepository(persistentTokenRepository())
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .loginPage("/login.html")
                    .successForwardUrl("/index.html")
                    .successHandler(commonLoginSuccessHandler)
                    .failureHandler(commonLoginFailureHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/token/refresh","/token/check", "/login", "/login.html").permitAll()
//                    .anyRequest().access("@commonHasPermission.hasPermision(request,authentication)")
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(commonAccessDeniedHandler)
                    .authenticationEntryPoint(commonAuthenticationEntryPoint)
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.sessionManagement()
//                //默认的session生成策略
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .sessionFixation().migrateSession()
//                //指定最大登录数
//                .maximumSessions(1)
//                // 当达到最大值时，旧用户被踢出后的操作
//                .expiredSessionStrategy(commonExpiredSessionStrategy)
//                // 当达到最大值时，是否保留已经登录的用户，为true，新用户无法登录；为 false，旧用户被踢出
//                .maxSessionsPreventsLogin(false);
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

    /**
     * remember持久化
     *
     * @param
     * @return PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

}
