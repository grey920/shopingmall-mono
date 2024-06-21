package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.user.service.UserService;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/emails/verification-request")
    public ResponseEntity<HttpStatus> sendVerificationEmail( @RequestParam("email") String email ) {

        userService.sendVerificationEmail( email );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PostMapping("/sign-up")
    public void signUp( @Valid @RequestBody UserSignUpRequest request ) {

        userService.signUp( request );
    }
}
