package com.sparta.shoppingmallmono.user.web.response;

import com.sparta.shoppingmallmono.user.domain.entity.User;
import lombok.Builder;

import java.util.UUID;
@Builder
public class UserResponse {
    private UUID id;

    private String email;

    private String name;

    private String password;

    private String phone;

    private String city;

    private String street;

    private String zipcode;

    private char gender;

    private boolean use2ndAuth;

    public static UserResponse of( User user ) {
        return UserResponse.builder()
                .id( user.getId() )
                .email( user.getEmail() )
                .name( user.getName() )
                .password( user.getPassword() )
                .phone( user.getPhone() )
                .city( user.getAddress().getCity() )
                .street( user.getAddress().getStreet() )
                .zipcode( user.getAddress().getZipcode() )
            .build();
    }
}
