package com.sparta.shoppingmallmono.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public LoginFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        // 요청에 담긴 유저 정보 가로챔
        String username = obtainUsername( request );
        String password = obtainPassword( request );

        System.out.println( "username = " + username );

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( username, password ); // DTO 처럼 담아서 보냄
        return authenticationManager.authenticate( authToken );
    }

    /**
     * 인증 성공시
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult ) throws IOException, ServletException {
        System.out.println(":::::::::: success ");
//        super.successfulAuthentication( request, response, chain, authResult );
    }

    /**
     * 인증 실패시
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, AuthenticationException failed ) throws IOException, ServletException {
        System.out.println(":::::::::: fail ");
//        super.unsuccessfulAuthentication( request, response, failed );
    }
}
