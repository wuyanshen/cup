package com.lvcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyanshen
 * @date 2020-03-19 1:00 上午
 * @discription 启动类
 *
 * 整合activiti会报错 GlobalAuthenticationConfigurerAdapter  cannot be opened because it does not exist
 * 这个是在 spring-boot-starter-security 依赖中的 属于安全配置类,
 * 而引入的activiti-spring-boot-starter-basic 依赖中也存在了一个自动安全配置类,  两个安全配置。
 * 排除activiti-spring-boot-starter-basic安全配置类
 */
@SpringBootApplication
public class CupApp {
    public static void main(String[] args) {
        SpringApplication.run(CupApp.class, args);
    }
}
