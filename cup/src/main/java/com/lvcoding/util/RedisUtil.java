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

package com.lvcoding.util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis封装RedisTemplate工具类
 *
 * @author wuyanshen
 */
@UtilityClass
public class RedisUtil {

    public RedisTemplate redisTemplate = SpringUtil.getBean("redisTemplate");

    /**
     * 模糊查询redis中的key
     */
    public Set<String> list(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 向redis中存储数据
     */
    public <T> ValueOperations<String,T> set(String key, T value) {
        ValueOperations<String,T> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        return ops;
    }

    /**
     * 向redis中存储数据(可以设置有效时间)
     */
    public <T> ValueOperations<String,T> set(String key, T value, long expire, TimeUnit timeUnit) {
        ValueOperations<String,T> ops = redisTemplate.opsForValue();
        ops.set(key, value, expire, timeUnit);
        return ops;
    }

    /**
     * 获取redis中存储的String数据
     */
    public <T> T get(String key) {
        ValueOperations<String,T> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 根据key删除redis中的数据
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

}
