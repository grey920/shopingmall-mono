package com.sparta.shoppingmallmono.user.web.request;

import com.sparta.shoppingmallmono.user.domain.entity.Address;
import com.sparta.shoppingmallmono.user.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 DTO
 */
@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotBlank( message = "이메일은 필수 입력 값입니다." )
    @Email( message = "이메일 형식이 올바르지 않습니다." )
    private String email;

    @NotBlank( message = "이름은 필수 입력 값입니다." )
    private String name;

    @NotBlank( message = "비밀번호는 필수 입력 값입니다." )
    private String password;

    @NotBlank( message = "전화번호는 필수 입력 값입니다." )
    private String phone;

    @NotBlank( message = "도시는 필수 입력 값입니다." )
    private String city;

    @NotBlank( message = "거리는 필수 입력 값입니다." )
    private String street;

    @NotBlank( message = "우편번호는 필수 입력 값입니다." )
    private String zipcode;

    private char gender;

    private boolean use2ndAuth;

    @Builder
    public UserSignUpRequest( String email, String name, String password, String phone, String city, String street, String zipcode, char gender, boolean use2ndAuth ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.gender = gender;
        this.use2ndAuth = use2ndAuth;
    }

    public User toEntity( String encodedPassword, String encodedPhone, Address encodedAddress ) {
        return User.builder()
            .email( email )
            .name( name )
            .password( encodedPassword )
            .phone( encodedPhone )
            .address( encodedAddress )
            .use2ndAuth( use2ndAuth )
            .gender( gender )
            .build();
    }

    public Address toAddressEntity( String encodedCity, String encodedStreet, String encodedZipcode ) {
        return Address.builder()
            .city( encodedCity )
            .street( encodedStreet )
            .zipcode( encodedZipcode )
            .build();
    }


}
