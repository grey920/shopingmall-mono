package com.sparta.shoppingmallmono.security;

import com.sparta.shoppingmallmono.redis.RedisUtil;
import com.sparta.shoppingmallmono.security.jwt.JWTFilter;
import com.sparta.shoppingmallmono.security.jwt.JWTUtil;
import com.sparta.shoppingmallmono.security.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //@EnableGlobalMethodSecurity 는 deprecated 되었다. @PreAuthorize, @PostAuthorize 등을 사용하기 위해 필요
@RequiredArgsConstructor
public class SecurityConfig {
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체, JWTUtil 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
        // CORS 설정 (프론트 서버와의 통신시 LoginFiler와 같은 필터들이 CORS에 걸리지 않도록 설정. 컨트롤러단은 MvcConfig에서 설정)
        http
            .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                @Override
                public CorsConfiguration getCorsConfiguration( HttpServletRequest request) {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 허용할 Origin (프론트 서버 3000 포트)
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders( Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);

                    configuration.setExposedHeaders(Collections.singletonList("Authorization")); // Authorization 헤더에 jwt 토큰을 넣어주기 위해 노출

                    return configuration;
                }
            })));

        //csrf disable
        http
            .csrf( AbstractHttpConfigurer::disable );

        //From 로그인 방식 disable
        http
            .formLogin( AbstractHttpConfigurer::disable );

        //http basic 인증 방식 disable
        http
            .httpBasic( AbstractHttpConfigurer::disable );

        //경로별 인가 작업
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/users/sign-up", "/auth/login", "/").permitAll()
                .requestMatchers( "/auth/reissue" ).permitAll() // 액세스 토큰 만료의 경우 재발급 위해 권한 없어도 permitAll
//                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated());

        http
            .addFilterBefore( jwtFilter(), LoginFilter.class ); // UsernamePasswordAuthenticationFilter 앞에 jwtFilter 추가
        http
            .addFilterAt( loginFilter(), UsernamePasswordAuthenticationFilter.class ); // UsernamePasswordAuthenticationFilter를 커스텀한 Login Filter로 대체

        //세션 설정
        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter( jwtUtil );
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
       return new LoginFilter( authenticationManager(authenticationConfiguration), jwtUtil, redisUtil );
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring().requestMatchers( PathRequest.toStaticResources().atCommonLocations() );
    }

}
