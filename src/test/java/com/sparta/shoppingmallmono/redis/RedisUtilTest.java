package com.sparta.shoppingmallmono.redis;

import com.sparta.shoppingmallmono.security.SecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:/application.yml")
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

    @DisplayName("토큰 조회 테스트")
    @Test
    void getRefreshTokenTest(){
        // given
        String key = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6InJlZnJlc2giLCJ1c2VybmFtZSI6Imt5ZXdvb25AbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTcxOTE5Mzk5NSwiZXhwIjoxNzE5MjgwMzk1fQ.vdfnebBL6Wf2pnrzfz3VYYRjTPTcaDiX6YuggBmLMqw"; // key: refreshToken value
        String value = "kyewoon@naver.com"; // value: username(email)

        // when
        String username = redisUtil.getRefreshToken( key );

        //then
        Assertions.assertThat(username).isEqualTo(value);
    }

}