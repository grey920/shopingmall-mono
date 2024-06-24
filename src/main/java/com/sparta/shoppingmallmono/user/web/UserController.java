package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.user.service.UserService;
import com.sparta.shoppingmallmono.user.web.request.CustomUserDetails;
import com.sparta.shoppingmallmono.user.web.request.UpdatePasswordRequest;
import com.sparta.shoppingmallmono.user.web.request.UpdateUserProfileRequest;
import com.sparta.shoppingmallmono.user.web.request.UserSignUpRequest;
import com.sparta.shoppingmallmono.user.web.response.EmailVerificationResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/emails/verification")
    public ResponseEntity verifyEmail( @RequestParam("email") String email, @RequestParam("code") String authCode ) {

        EmailVerificationResult response = userService.verifyEmail( email, authCode );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp( @Valid @RequestBody UserSignUpRequest request ) {

        userService.signUp( request );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

    /**
     * 비밀번호 변경
     * TODO: 비밀번호 변경시 모든 기기 로그아웃
     * @param user
     * @param request
     * @return
     */
    @PutMapping("/password")
    public ResponseEntity<HttpStatus> changePassword( @AuthenticationPrincipal CustomUserDetails user, @RequestBody UpdatePasswordRequest request ) {

        userService.changePassword( user.getUsername(), request );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpStatus> updateProfile( @AuthenticationPrincipal CustomUserDetails user, @RequestBody UpdateUserProfileRequest request ) {

        userService.updateProfile( user.getUsername(), request );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
