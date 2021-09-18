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

package com.lvcoding.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 使用fastjson重写redis序列化和反序列化
 *
 * @author wuyanshen
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET;

    private Class<T> clazz;

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        /**
         * 开启fastjson autotype功能（不开启，造成EntityWrapper<T>中的T无法正常解析）
         */
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 反序列化
     */
    @Override
    public T deserialize(@Nullable byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

    /**
     * 序列化
     */
    @Override
    public byte[] serialize(@Nullable Object t) {
        if (t == null) {
            return new byte[0];
        }
        /**
         * SerializerFeature.WriteClassName 这个很关键，
         * 这样序列化后的json中就会包含这个类的全称 ==> "@type": "com.lvcoding.entity.SysUser",
         * 在反序列化的时候，就可以直接转换成SysUser对象了
         */
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
}
