package com.sparta.shoppingmallmono.security;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class EncryptionUtil {
    private final StringEncryptor jasyptStringEncryptor;

    public String encrypt(String data) {
        return jasyptStringEncryptor.encrypt(data);
    }

    public String decrypt(String data) {
        return jasyptStringEncryptor.decrypt(data);
    }

}
