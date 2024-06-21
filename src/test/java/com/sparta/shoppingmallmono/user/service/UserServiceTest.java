package com.sparta.shoppingmallmono.user.service;

import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private UserSignUpRequest userSignUpRequest;

    @BeforeEach
    void setUp() {
        userSignUpRequest = UserSignUpRequest.builder()
            .name( "정겨운" )
            .email( "charminggw@gmail.com" )
            .password( "password" )
            .phone( "01012345678" )
            .city( "서울 영등포구 커피로19길 6" )
            .street( "101동 9304호" )
            .zipcode( "12345" )
            .use2ndAuth( false )
            .gender( 'F' )
            .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("[회원가입] 회원가입 성공")
    void signUp() {
        // given

        // when
        userService.signUp( userSignUpRequest );

        // then
        userRepository.findByEmail( userSignUpRequest.getEmail() )
            .ifPresentOrElse(
                user -> {
                    assertEquals( userSignUpRequest.getName(), user.getName() );
                    assertEquals( userSignUpRequest.getEmail(), user.getEmail() );
                    assertEquals( userSignUpRequest.getPhone(), user.getPhone() );
                    assertEquals( userSignUpRequest.getCity(), user.getAddress().getCity() );
                    assertEquals( userSignUpRequest.getStreet(), user.getAddress().getStreet() );
                    assertEquals( userSignUpRequest.getZipcode(), user.getAddress().getZipcode() );
                    assertEquals( userSignUpRequest.isUse2ndAuth(), user.isUse2ndAuth() );
                },
                () -> fail( "회원가입 실패" )
            );

    }




}