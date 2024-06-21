package com.sparta.shoppingmallmono.message;

import com.sparta.shoppingmallmono.message.email.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendEmail( EmailRequest emailRequest) {
        SimpleMailMessage email = emailRequest.toSimpleMailMessage();

        try {
            javaMailSender.send(email);

        } catch (Exception e) {
            log.error("MailService.sendEmail exception occur toEmail: {}, title: {}, text: {}", emailRequest.getTo(), emailRequest.getTitle(), emailRequest.getMessage());
            throw new IllegalArgumentException("이메일 전송에 실패했습니다.");
        }
    }
}
