package com.bookingticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .authorizeHttpRequests(auth -> auth
                        // Các URL không yêu cầu xác thực
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/user/confirmSelection",
                                "/plugins/**",
                                "/forgot-password",
                                "/reset-password",
                                "/user/step2",
                                "/user/step2/submit",
                                "/user/step3",
                                "/oauth2/**",
                                "/error",
                                "/seats/**",
                                "/index",
                                "/img/**",
                                "/login/**",
                                "/webjars/**",
                                "/customer-info",
                                "/favicon.ico",
                                "/submit-info",
                                "/book",
                                "/user/submit",
                                "user/display",
                                "/step2/submit",
                                "/book/submit",
                                "/api/**", // Cho phép truy cập các API
                                "/admin",
                                "/admin/**",
                                "/payment",
                                "/user",
                                "/admin/user-list"
                        ).permitAll()
                        // Các URL khác yêu cầu xác thực
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        // Cấu hình trang đăng nhập
                        .loginPage("/")
                        // Điều hướng khi đăng nhập thành công
                        .defaultSuccessUrl("/oauth2/callback/google", true)
                        // Điều hướng khi đăng nhập thất bại
                        .failureUrl("/login?error=true")
                        .failureHandler((request, response, exception) -> {
                            System.out.println("Lỗi xác thực OAuth2: " + exception.getMessage());
                            exception.printStackTrace();
                            response.sendRedirect("/login?error=true");
                        })
                )
                // Tắt CSRF để sử dụng API RESTful, cần cẩn thận khi áp dụng vào sản phẩm thực tế
                .csrf().disable();

        return http.build();
    }

}
