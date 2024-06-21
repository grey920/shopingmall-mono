package com.sparta.shoppingmallmono.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig( SecurityConfig.class)
public class BCryptPasswordEncoderTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("암호화된 비밀번호가 일치하는지 확인한다.")
    @Test
    void match_password(){
        String rawPassword = "my-secure-password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
