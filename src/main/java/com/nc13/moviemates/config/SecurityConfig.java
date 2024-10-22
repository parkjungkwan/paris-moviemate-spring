package com.nc13.moviemates.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nc13.moviemates.serviceImpl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info(" ##### 시큐리티 필터 체인 진입 ##### ");
        http
        .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**","/css/**","/profile/**","/vendors/**","/style.css/**",
                "/fonts/**",  "/less/**",  "/images/**", "/js/**", "/lib/**","/favicon.ico").permitAll()
                .requestMatchers(
                // atom    
                "/",
                "/api/admin/**",
                "/api/images/**",
                "/api/chart/**",
                "/api/movie/**",
                "/api/payment/**",
                "/api/poster/**",
                "/api/reservation/**",
                "/api/review/**",
                "/api/schedule/**",
                "/api/seat/**",
                "/api/theater/**",
                "/api/user/**",
                "/api/crawl/**",
                "/api/wish/**"
                
                ).permitAll()
                .requestMatchers("/**").authenticated()
       
            )
   

            // .formLogin(form -> form
            //     .loginPage("/api/users/login")
            //     .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
            //     .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
            //     .defaultSuccessUrl("/")
            //     .failureForwardUrl("/api/users/login-error")
            // )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .userDetailsService(userDetailsServiceImpl)
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403.html")
            )
            // OAuth 로그인 설정 추가
        .oauth2Login(oauth2 -> oauth2
        .loginPage("/loginForm") // 로그인 페이지 URL
        .userInfoEndpoint(userInfo -> userInfo
            .userService(userDetailsServiceImpl) // OAuth 로그인 시 서비스 매핑
        )
    );

        return http.build();
    }


    
}
