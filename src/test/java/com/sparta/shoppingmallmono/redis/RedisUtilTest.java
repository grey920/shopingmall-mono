package com.sparta.shoppingmallmono.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilTest {
    @Autowired
    RedisUtil redisUtil;

    @DisplayName("데이터 저장")
    @Test
    void setData(){
        // given
        String key = "testKey";
        String value = "testValue";

        // when
        redisUtil.setDataExpire(key, value, 100L);

        //then
        assertEquals(value, redisUtil.getData(key));
        System.out.println("value = " + value);
    }

}