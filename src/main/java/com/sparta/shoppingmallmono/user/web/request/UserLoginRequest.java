package com.sparta.shoppingmallmono.user.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank( message = "이메일은 필수 입력 값입니다." )
    private String email;

    @NotBlank( message = "비밀번호는 필수 입력 값입니다." )
    private String password;

    @Builder
    public UserLoginRequest( String email, String password ) {
        this.email = email;
        this.password = password;
    }
}
