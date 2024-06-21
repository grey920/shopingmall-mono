package com.sparta.shoppingmallmono.user.service;

import com.sparta.shoppingmallmono.security.EncryptionUtil;
import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EncryptionUtil encryptionUtil;


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
                    assertTrue( passwordEncoder.matches( userSignUpRequest.getPassword(), user.getPassword() ) );
                    assertEquals( userSignUpRequest.getPhone(), encryptionUtil.decrypt( user.getPhone() ) );
                    assertEquals( userSignUpRequest.getCity(), encryptionUtil.decrypt( user.getAddress().getCity() ) );
                    assertEquals( userSignUpRequest.getStreet(), encryptionUtil.decrypt( user.getAddress().getStreet() ) );
                    assertEquals( userSignUpRequest.getZipcode(), encryptionUtil.decrypt( user.getAddress().getZipcode() ) );
                    assertEquals( userSignUpRequest.isUse2ndAuth(), user.isUse2ndAuth() );
                },
                () -> fail( "회원가입 실패" )
            );

    }

    @DisplayName("이메일 인증을 위한 랜덤 숫자 전송")
    @Test
    void sendVerificationEmail(){
        // given
        String email = "kyewoon@naver.com";

        // when
        userService.sendVerificationEmail(email);

        //then

    }


}