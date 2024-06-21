package com.sparta.shoppingmallmono.user.service;

import com.sparta.shoppingmallmono.security.EncryptionUtil;
import com.sparta.shoppingmallmono.user.domain.entity.Address;
import com.sparta.shoppingmallmono.user.domain.entity.User;
import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final EncryptionUtil encryptionUtil;

    /**
     * 회원가입
     * - 비밀번호 단방향 암호화
     * - 주소, 전화번호 양방향 암호화
     * - 이메일 중복 확인
     * @param request
     */
    @Transactional
    public void signUp( UserSignUpRequest request ) {
        // 이메일 중복 확인
        isDuplicatedEmail( request.getEmail() );

        // 비밀번호 단방향 암호화
        String encodedPassword = request.getPassword(); // 비밀번호 단방향 암호화

        // 요구사항 필수) 주소, 전화번호 양방향 암호화
        String encodedPhone = encryptionUtil.encrypt( request.getPhone() ); // 전화번호 양방향 암호화
        Address encodedAddress = makeEncryptedAddress(request);


        userRepository.save( request.toEntity(encodedPassword, encodedPhone, encodedAddress) );

    }

//    =================================================================

    private Address makeEncryptedAddress( UserSignUpRequest request ) {
        String encodedCity = encryptionUtil.encrypt( request.getCity() ); // 도시 양방향 암호화
        String encodedStreet = encryptionUtil.encrypt( request.getStreet() ); // 거리 양방향 암호화
        String encodedZipcode = encryptionUtil.encrypt( request.getZipcode() ); // 우편번호 양방향 암호화

        return request.toAddressEntity(encodedCity, encodedStreet, encodedZipcode);

    }


    private void isDuplicatedEmail( String email ) {
        if ( userRepository.findByEmail( email ).isPresent() ) {
            throw new IllegalArgumentException( "이미 가입된 이메일입니다." );
        }
    }

}
