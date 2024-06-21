package com.sparta.shoppingmallmono.message.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.mail.SimpleMailMessage;

@Getter
@Builder
@AllArgsConstructor
public class EmailRequest {
    private String to;
    private String title;
    private String message;

    public SimpleMailMessage toSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);
        return simpleMailMessage;
    }
}
