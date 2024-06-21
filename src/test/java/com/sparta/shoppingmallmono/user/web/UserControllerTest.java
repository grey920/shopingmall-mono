package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }


    @Test
    public void 회원등록실패() throws Exception {
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
//            .name( "정겨운" )
            .email( "charminggw@gmail.com" )
            .password( "password" )
            .phone( "01012345678" )
            .city( "서울 영등포구 커피로19길 6" )
            .street( "101동 9304호" )
            .zipcode( "12345" )
            .use2ndAuth( false )
            .gender( 'F' )
            .build();

        String url = "http://localhost:" + port + "/api/users/sign-up";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity( url, userSignUpRequest, Object.class );

        // then
        Assertions.assertThat( responseEntity.getStatusCode() ).isNotEqualTo( HttpStatus.CREATED );

    }

    @DisplayName("회원등록성공")
    @Test
    void 회원등록성공(){
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
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
        String url = "http://localhost:" + port + "/api/users/sign-up";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity( url, userSignUpRequest, Object.class );

        //then
        Assertions.assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.CREATED );
    }

}