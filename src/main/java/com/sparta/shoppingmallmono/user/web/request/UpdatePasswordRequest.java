package com.sparta.shoppingmallmono.user.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePasswordRequest {

    private String currentPassword;
    private String newPassword;

}
