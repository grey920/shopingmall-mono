package com.sparta.shoppingmallmono.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncryptionUtilTest {
    @Autowired
    EncryptionUtil encryptionUtil;

    @ParameterizedTest
    @DisplayName("원본과 암호화된 문자열을 복호화한 결과가 같아야 한다.")
    @ValueSource(strings = {"01012345678", "서울시 강남구 zzz 123", "999동 105호"})
    void encryptTest(String input){
        // given
        // when
        String encrypted = encryptionUtil.encrypt(input);
        String decrypted = encryptionUtil.decrypt(encrypted);

        //then
        assertNotEquals(input, encrypted);
        assertEquals(input, decrypted);

    }

    @DisplayName("")
    @Test
    void decryptTest(){
        // given
        String encrypted = "uxlQlWnMNBb1Tf1sKBWNxA4UFeFfh7oC";

        // when
        String decrypted = encryptionUtil.decrypt(encrypted);

        //then
        System.out.println("decrypted = " + decrypted);
    }

}