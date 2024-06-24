package com.sparta.shoppingmallmono.user.service;

import com.sparta.shoppingmallmono.message.EmailService;
import com.sparta.shoppingmallmono.message.email.EmailRequest;
import com.sparta.shoppingmallmono.redis.RedisUtil;
import com.sparta.shoppingmallmono.security.EncryptionUtil;
import com.sparta.shoppingmallmono.user.domain.entity.Address;
import com.sparta.shoppingmallmono.user.domain.entity.User;
import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.UpdatePasswordRequest;
import com.sparta.shoppingmallmono.user.web.request.UpdateUserProfileRequest;
import com.sparta.shoppingmallmono.user.web.request.UserLoginRequest;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import com.sparta.shoppingmallmono.user.web.response.EmailVerificationResult;
import com.sparta.shoppingmallmono.user.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private static final String AUTH_CODE_PREFIX = "authCode:";
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final EmailService emailService;
    private final UserRepository userRepository;

    private final EncryptionUtil encryptionUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;


    /**
     * 로그인
     * @param request
     */
    public UserResponse login( UserLoginRequest request ) {
        User user = userRepository.findByEmail( request.getEmail() )
                .orElseThrow( () -> new IllegalArgumentException( "회원 정보가 없습니다." ) );

        if ( !passwordEncoder.matches( request.getPassword(), user.getPassword() ) ) {
            throw new IllegalArgumentException( "비밀번호가 일치하지 않습니다." );
        }

        return UserResponse.of( user );
    }

    /**
     * 이메일 인증 코드 전송
     * @param email
     */
    public void sendVerificationEmail( String email ) {
        String authCode = createAuthCode();
        emailService.sendEmail( EmailRequest.builder()
                .to( email )
                .title( "회원가입 인증 코드 발급 안내" )
                .message( "회원가입 인증 코드 : " + authCode )
                .build() );

        // Redis에 인증 코드 저장 (key: "authCode:" + email, value: authCode, expiration: authCodeExpirationMillis)
        redisUtil.setDataExpire( AUTH_CODE_PREFIX + email, authCode, authCodeExpirationMillis );
    }

    /**
     * 이메일 인증 코드 확인
     * @param email
     * @param authCode
     */
    public EmailVerificationResult verifyEmail( String email, String authCode ) {
        String redisAuthCode = redisUtil.getData( AUTH_CODE_PREFIX + email );

        return EmailVerificationResult.of( authCode.equals( redisAuthCode ) );
    }

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
        String encodedPassword = passwordEncoder.encode( request.getPassword() );

        // 요구사항 필수) 주소, 전화번호 양방향 암호화
        String encodedPhone = encryptionUtil.encrypt( request.getPhone() );

        userRepository.save( request.toEntity(encodedPassword, encodedPhone) );

    }

    /**
     * 비밀번호 변경
     * @param email
     * @param request
     */
    @Transactional
    public void changePassword( String email, UpdatePasswordRequest request ) {
        // 회원 조회
        User user = userRepository.findByEmail( email )
                .orElseThrow( () -> new IllegalArgumentException( "회원 정보가 없습니다." ) );

        // 비밀번호 일치 확인
        if ( !passwordEncoder.matches( request.getCurrentPassword(), user.getPassword() ) ) {
            throw new IllegalArgumentException( "비밀번호가 일치하지 않습니다." );
        }

        // 새 비밀번호 단방향 암호화
        String encodedNewPassword = passwordEncoder.encode( request.getNewPassword() );
        user.updatePassword( encodedNewPassword );

    }

    /**
     * 프로필 수정
     * @param email
     * @param request
     */
    @Transactional
    public void updateProfile( String email, UpdateUserProfileRequest request ) {
        // 회원 조회
        User user = userRepository.findByEmail( email )
                .orElseThrow( () -> new IllegalArgumentException( "회원 정보가 없습니다." ) );

        // 주소 암호화 후 업데이트
        if ( request.getCity() != null || request.getStreet() != null || request.getZipcode() != null ) {
            Address address = Optional.ofNullable( user.getAddress() ).orElse( new Address() );
            address.updateAddress( makeEncryptedAddress( request.getCity(), request.getStreet(), request.getZipcode() ) );
            user.updateAddress( address );
        }

        if ( request.getPhone() != null ) {
            // 전화번호 암호화 후 업데이트
            String encodedPhone = encryptionUtil.encrypt( request.getPhone() );
            user.updatePhone( encodedPhone );
        }
    }

//    =================================================================

    private Address makeEncryptedAddress( String city, String street, String zipcode ) {
        String encodedCity = encryptionUtil.encrypt( city ); // 도시 양방향 암호화
        String encodedStreet = encryptionUtil.encrypt( street ); // 거리 양방향 암호화
        String encodedZipcode = encryptionUtil.encrypt( zipcode ); // 우편번호 양방향 암호화

        return Address.builder()
                .city( encodedCity )
                .street( encodedStreet )
                .zipcode( encodedZipcode )
                .build();

    }


    private void isDuplicatedEmail( String email ) {
        if ( userRepository.findByEmail( email ).isPresent() ) {
            throw new IllegalArgumentException( "이미 가입된 이메일입니다." );
        }
    }


    private String createAuthCode() {
        int length = 6;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for ( int i = 0; i < length; i++ ) {
                authCode.append( random.nextInt(10) );
            }
            return authCode.toString();
        } catch ( Exception e ) {
            log.error( "인증 코드 생성 실패" );
            throw new IllegalArgumentException( "인증 코드 생성 실패" );
        }
    }

}
