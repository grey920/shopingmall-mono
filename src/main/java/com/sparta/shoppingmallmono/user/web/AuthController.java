package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.user.web.request.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("login")
    public String login( @Valid @RequestBody UserLoginRequest request ) {

        return "success";
    }
}
