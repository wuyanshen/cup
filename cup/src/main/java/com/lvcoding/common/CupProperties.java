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

package com.lvcoding.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 描述
 * @Date 2020-11-05 6:59 下午
 * @Author wuyanshen
 */
@Data
@Component
@ConfigurationProperties(prefix = "cup")
public class CupProperties {

    /**
     * 是否开启演示模式
     */
    private boolean enableDemo = false;

    /**
     * 验证码类型
     */
    private String captchaType = "math";

    /**
     * redis cache注解失效时间(单位：分)，默认60分钟
     */
    private int cacheExpire = 60;

    /**
     * 不需要校验token的url
     */
    private List<String> tokenIgnoreUrl = new ArrayList<>();
}
