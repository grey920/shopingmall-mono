package com.sparta.shoppingmallmono.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shoppingmallmono.user.web.request.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JWTUtil jwtUtil;

    public LoginFilter( AuthenticationManager authenticationManager, JWTUtil jwtUtil ) {
        this.jwtUtil = jwtUtil;
        setAuthenticationManager( authenticationManager );
        setFilterProcessesUrl( "/auth/login" ); // 필터가 작동할 경로
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        // 요청에 담긴 유저 정보 가로챔
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authToken = null;
        try {
            // JSON 데이터를 Map으로 파싱
            Map< String, String > credentials = mapper.readValue( request.getInputStream(), Map.class );
            String username = credentials.get( "email" );
            String password = credentials.get( "password" );


            authToken = new UsernamePasswordAuthenticationToken( username, password );
        }
        catch ( IOException e ) {
            throw new AuthenticationException( "Error parsing authentication request body" ) {
            };
        }

        return getAuthenticationManager().authenticate( authToken );
    }

    /**
     * 인증(로그인) 성공시
     *
     * @param request
     * @param response
     * @param chain
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) throws IOException, ServletException {
        //유저 정보
        String username = authentication.getName();

        Collection< ? extends GrantedAuthority > authorities = authentication.getAuthorities();
        Iterator< ? extends GrantedAuthority > iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt( "access", username, role, 600000L ); // 10분
        String refresh = jwtUtil.createJwt( "refresh", username, role, 86400000L ); // 1일

        //응답 설정
        response.setHeader( "access", access ); // 응답 헤더에 액세스 토큰 추가
        response.addCookie( createCookie( "refresh", refresh ) ); // 응답 쿠키에 리프레시 토큰 추가
        response.setStatus( HttpStatus.OK.value() ); // 응답 상태 코드 200
    }

    /**
     * 인증 실패시
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, AuthenticationException failed ) throws IOException, ServletException {
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 쿠키 생명주기 1일
        //cookie.setSecure(true); // SSL 통신이면
        //cookie.setPath("/"); // 모든 경로에서 접근 가능하도록
        cookie.setHttpOnly(true); // JS에서 접근 불가

        return cookie;
    }
}
