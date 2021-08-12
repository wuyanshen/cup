package com.lvcoding.config;


import com.lvcoding.common.CupProperties;
import com.lvcoding.redis.FastJson2JsonRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis配置类
 *
 * @author wuyanshen
 */
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final CupProperties cupProperties;

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FastJson2JsonRedisSerializer(Object.class));
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 配置cache缓存
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config
                // 设置缓存的默认过期时间(分)，也是使用Duration设置
                .entryTtl(Duration.ofMinutes(cupProperties.getCacheExpire()))
                // 不缓存空值
                .disableCachingNullValues()
                // 设置缓存序列化数据为json格式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJson2JsonRedisSerializer(Object.class)));


        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("cup", config);

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .withInitialCacheConfigurations(configMap)
                .build();

        return cacheManager;
    }

}
