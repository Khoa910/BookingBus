package com.bookingticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/user/step2",
                        "/css/**",
                        "/js/**",
                        "/plugins/**",
                        "/error",
                        "/img/**",
                        "/book",
                        "/api/**" // Cho phép truy cập các API
                ).permitAll() // Không yêu cầu xác thực với các URL này
                .anyRequest().authenticated() // Các URL khác yêu cầu đăng nhập
                .and()
                .oauth2Login() // Kích hoạt OAuth2 Login
                .loginPage("/login") // URL của trang login tùy chỉnh
                .defaultSuccessUrl("/home", true) // Redirect sau khi login thành công
                .failureUrl("/login?error=true") // Redirect nếu login thất bại
                .and()
                .csrf().disable(); // Tắt CSRF (chỉ nên làm với API RESTful)

        return http.build();
    }
}
