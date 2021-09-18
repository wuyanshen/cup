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

package com.lvcoding.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多租户属性配置类
 * @Date 2020-08-28 10:21 上午
 * @Author wuyanshen
 */
@Data
@Component
@ConfigurationProperties(prefix = "cup.tenant")
public class TenantProperties {

    /**
     *  是否开启租户功能，默认不开启（false）
     */
    private boolean enable = false;

    /**
     *  租户id字段名称
     */
    private String column = "tenant_id";


    /**
     * 不需要过滤的表，默认为空
     */
    private List<String> ignoreTables = new ArrayList<>();

}
