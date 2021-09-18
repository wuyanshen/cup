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

package com.lvcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author wuyanshen
 * @date 2020-03-19 1:00 上午
 * @description 启动类
 */
@SpringBootApplication
public class CupApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CupApp.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("server.port");
        String env = environment.getProperty("spring.profiles.active");

        System.out.println(
                "\n" +
                "\n" +
                "              _______  ______\n" +
                "             / ___/ / / / __ \\\n" +
                "            / /__/ /_/ / /_/ /\n" +
                "            \\___/\\__,_/ .___/\n" +
                "                     /_/       V1.3\n" +
                "\n" +
                "             cup启动成功！" +
                "\n" +
                "             端口：" + port +
                "\n" +
                "             环境：" + env +
                "\n" +
                "\n"
        );
    }
}
