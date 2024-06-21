package com.sparta.shoppingmallmono.user.domain.repository;

import com.sparta.shoppingmallmono.user.domain.entity.Address;
import com.sparta.shoppingmallmono.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        Address address = Address.builder()
            .city( "서울" )
            .street( "영등포구 커피로19길 6" )
            .zipcode( "12345" )
            .build();

        user = User.builder()
            .name( "정겨운" )
            .email( "charminggw@gmail.com" )
            .password( "password" )
            .phone( "01012345678" )
            .address( address )
            .use2ndAuth( false )
            .gender( 'F' )
            .build();
    }

    @DisplayName("회원 저장 테스트")
    @Test
    void saveTest(){
        // given

        // when
        User savedUser = userRepository.save( user );

        //then
        System.out.println( savedUser.getId() );
        assertEquals( user.getName(), savedUser.getName() );

    }

    @DisplayName("이메일로 회원 조회 테스트")
    @Test
    void findByEmail(){
        // given
        User saved = userRepository.save( user );

        // when
        Optional< User > byEmail = userRepository.findByEmail( saved.getEmail() );

        //then
        assertTrue( byEmail.isPresent() );
        assertEquals( saved.getEmail(), byEmail.get().getEmail() );
    }


}