package com.sparta.shoppingmallmono.user.web.response;

import lombok.Getter;

@Getter
public class EmailVerificationResult {
    private final String message;
    private final boolean success;

    private EmailVerificationResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public static EmailVerificationResult of(boolean isVerified) {
        String message = isVerified ? "이메일 인증이 완료되었습니다." : "이메일 인증에 실패했습니다.";
        return new EmailVerificationResult(message, isVerified);
    }
}
