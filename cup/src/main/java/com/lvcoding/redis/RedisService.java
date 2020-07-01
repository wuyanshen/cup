package com.lvcoding.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis封装RedisTemplate工具类
 *
 * @author wuyanshen
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

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
