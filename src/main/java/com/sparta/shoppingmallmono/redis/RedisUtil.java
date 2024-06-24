package com.sparta.shoppingmallmono.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isExistByRefresh( String refresh ) {
        return Boolean.TRUE.equals( redisTemplate.hasKey( refresh ) );
    }
    public void saveRefreshToken(String refreshToken, String username, Long expiredTime) {
        redisTemplate.opsForValue().set(refreshToken, username, expiredTime, TimeUnit.MILLISECONDS);
    }
    public String getRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    public void setDataExpire(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
