package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.user.service.UserService;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp( @Valid @RequestBody UserSignUpRequest request ) {

        userService.signUp( request );
    }
}
