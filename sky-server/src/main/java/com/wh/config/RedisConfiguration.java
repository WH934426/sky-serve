package com.wh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 */
@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        try {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            // 设置redis的连接工厂对象
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            // 设置redis key的序列化器
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            log.info("创建redis模板对象成功");
            return redisTemplate;
        } catch (Exception e) {
            log.error("创建redis模板对象失败");
            throw new RuntimeException("创建redis模板对象失败",e);
        }
    }
}
