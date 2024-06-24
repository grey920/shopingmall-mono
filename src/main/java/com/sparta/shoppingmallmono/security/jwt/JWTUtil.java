package com.sparta.shoppingmallmono.security.jwt;

import com.sparta.shoppingmallmono.user.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWTUtil 클래스
 * - username, role, expiredMs 검증
 *
 */
@Component
public class JWTUtil {
    private SecretKey secretKey;
    private final CustomUserDetailsService customUserDetailsService;
    public JWTUtil( @Value("${jwt.secret}") String secret, CustomUserDetailsService customUserDetailsService ) {
        // 문자열 secret 키를 객체 변수로 암호화 해서 저장
        secretKey = new SecretKeySpec(secret.getBytes( StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.customUserDetailsService = customUserDetailsService;
    }

    //======================> JWT 토큰 검증
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername( username );
        return new UsernamePasswordAuthenticationToken( userDetails, "", userDetails.getAuthorities() );
    }




    // ======================> JWT 토큰 생성
    public String createJwt(String category,String username, String role, Long expiredMs) {

        return Jwts.builder()
            .claim("category", category) // 토큰의 유형
            .claim("username", username)
            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis())) // 현재 발행 시간
            .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시간 (현재 시간 + 만료 시간)
            .signWith(secretKey) // 암호화 진행
            .compact(); // 토큰 생성
    }
}
