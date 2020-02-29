package com.goj.restservice.config;

import com.goj.restservice.entity.ResultRedis;
import com.goj.restservice.entity.SubmissionRedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Integer, SubmissionRedis> submissionRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Integer, SubmissionRedis> template = new RedisTemplate<Integer, SubmissionRedis>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<SubmissionRedis> serializer = new Jackson2JsonRedisSerializer<SubmissionRedis>(
                SubmissionRedis.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    @Bean
    public RedisTemplate<Integer, ResultRedis> resultRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Integer, ResultRedis> template = new RedisTemplate<Integer, ResultRedis>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<ResultRedis> serializer = new Jackson2JsonRedisSerializer<ResultRedis>(
                ResultRedis.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
}