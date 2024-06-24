package com.sparta.shoppingmallmono.security.jwt;

import com.sparta.shoppingmallmono.redis.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * 로그아웃 필터 커스텀

 */
public class CustomLogoutFilter extends GenericFilterBean {
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    public CustomLogoutFilter( JWTUtil jwtUtil, RedisUtil redisUtil ) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain ) throws IOException, ServletException {
        doFilter(( HttpServletRequest ) servletRequest, ( HttpServletResponse ) servletResponse, filterChain);
    }
    private void doFilter( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/auth/logout$")) { // 모든 요청 중 /auth/logout 요청이 아닌 경우 -> 다음 필터로 넘어감

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) { // POST 요청이 아닌 경우 -> 다음 필터로 넘어감

            filterChain.doFilter(request, response);
            return;
        }


        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        //refresh null check
        if (refresh == null) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //expired check -> 이미 만료된 토큰이면 BAD_REQUEST 반환하거나 이미 만료되었다고 알려주기
        try {
            jwtUtil.isExpired(refresh);
        } catch ( ExpiredJwtException e) {

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰이 refresh인지/access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //redis에 저장되어 있는지 확인
        boolean isExist = redisUtil.isExistByRefresh( refresh );
        if (!isExist) {

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        // <--------------------- 토큰 검증 끝

        //로그아웃 진행
        //Refresh 토큰 저장소에서 제거 -> reissue 방지
        redisUtil.deleteRefreshToken( refresh );

        //Refresh 토큰 Cookie 값 0 -> 쿠키를 지워주기 위해
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
