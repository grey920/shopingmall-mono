package com.sparta.shoppingmallmono.security.jwt;

import com.sparta.shoppingmallmono.user.domain.entity.User;
import com.sparta.shoppingmallmono.user.web.request.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter { // OncePerRequestFilter: 요청당 한 번만 실행되는 필터

    private final JWTUtil jwtUtil;

    /**
     * JWT 검증
     * JWTFilter의 doFilterInternal 메서드는 JWT 토큰을 검증하고, 토큰이 유효하면 SecurityContext에 인증 정보를 저장한다.
     */
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader( "access" ); // 헤더에 담긴 액세스 토큰을 꺼냄

        // 토큰이 없다면 다음 필터로 넘김 (권한이 필요없는 요청인 경우)
        if ( accessToken == null ) {

            filterChain.doFilter( request, response );

            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired( accessToken );
        }
        catch ( ExpiredJwtException e ) { // 만료되면 ExpiredJwtException 발생

            //response body
            PrintWriter writer = response.getWriter();
            writer.print( "access token expired" );

            //response status code [중요] 다음 필터로 넘기지 않음
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시).
        String category = jwtUtil.getCategory( accessToken );

        if ( !category.equals( "access" ) ) { // 다음 필터로 넘기지 않음

            //response body
            PrintWriter writer = response.getWriter();
            writer.print( "invalid access token" );

            //response status code
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            return;
        }

        // username, role 값을 획득
        String username = jwtUtil.getUsername( accessToken );
        String role = jwtUtil.getRole( accessToken );
        Authentication authentication = jwtUtil.getAuthentication( accessToken );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        filterChain.doFilter( request, response );
    }
}
