package com.sparta.shoppingmallmono.user.web;

import com.sparta.shoppingmallmono.security.jwt.JWTUtil;
import com.sparta.shoppingmallmono.user.service.AuthService;
import com.sparta.shoppingmallmono.user.service.UserService;
import com.sparta.shoppingmallmono.user.web.request.UserLoginRequest;
import com.sparta.shoppingmallmono.user.web.response.UserResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login( @Valid @RequestBody UserLoginRequest request ) {

        UserResponse response = userService.login( request );
        return ResponseEntity.status( HttpStatus.OK ).body( response );

    }

    /**
     * 토큰 재발급
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue( HttpServletRequest request, HttpServletResponse response) {

        return authService.reissueToken(request, response);
    }

}
