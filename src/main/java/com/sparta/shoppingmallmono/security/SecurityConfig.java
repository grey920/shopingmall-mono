package com.sparta.shoppingmallmono.security;

import com.sparta.shoppingmallmono.security.jwt.JWTUtil;
import com.sparta.shoppingmallmono.security.jwt.LoginFilter;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //@EnableGlobalMethodSecurity 는 deprecated 되었다. @PreAuthorize, @PostAuthorize 등을 사용하기 위해 필요
@RequiredArgsConstructor
public class SecurityConfig {
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체, JWTUtil 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

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
//                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated());

        http
            .addFilterAt( loginFilter(), UsernamePasswordAuthenticationFilter.class ); // UsernamePasswordAuthenticationFilter를 커스텀한 Login Filter로 대체

        //세션 설정
        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public LoginFilter loginFilter() throws Exception {
       return new LoginFilter( authenticationManager(authenticationConfiguration), jwtUtil );
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring().requestMatchers( PathRequest.toStaticResources().atCommonLocations() );
    }

}
