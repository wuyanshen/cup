/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyanshen
 * @date 2020-03-19 1:00 上午
 * @description 启动类
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
