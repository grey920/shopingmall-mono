package com.sparta.shoppingmallmono.user.web.request;

import lombok.Getter;

/**
 * [요구사항]
 * 주소와 전화번호를 수정한다.
 */
@Getter
public class UpdateUserProfileRequest {
    private String phone;

    private String city;

    private String street;

    private String zipcode;
}
