package com.sparta.shoppingmallmono.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    /**
     * RedisConnectionFactory 빈 등록
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(password);
        return new LettuceConnectionFactory(config);
    }

    /**
     * RedisTemplate 빈 등록
     * - RedisConnection은 binary 데이터를 다루기 때문에 String 타입으로 변환하는 작업이 필요
     * - Redis에 Key, Value를 저장하고 가져오는데 사용
     * - 다양한 타입에 대한 연산 제공
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();  // Key, Value를 String 타입으로 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
