package com.sparta.shoppingmallmono.message;

import com.sparta.shoppingmallmono.message.email.EmailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.config.location=classpath:/application.yml")
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void basicSend(){
        // given

        // when
        EmailRequest emailRequest = EmailRequest.builder()
            .to( "kyewoon@naver.com" )
            .title( "테스트 메일 발송" )
            .message( "JUnit에서 보내는 테스트 메일" )
            .build();
        emailService.sendEmail( emailRequest );

        //then
    }
}