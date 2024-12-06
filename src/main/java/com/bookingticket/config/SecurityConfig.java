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
                                "/mobile-vnpay",
                                "/submitOrder",
                                "/admin-schebus/trip",
                                "../js/user.js",
                                "../js/user.js",
                                "/mobile-vnpay",
                                "/submitOrder",
                                "/vnpay-payment-return",
                                "/user/confirmSelection",
                                "/plugins/**",
                                "/forgot-password",
                                "/reset-password",
                                "/user/step2",
                                "/user/step2/submit",
                                "/user/step3",
                                "/oauth2/**",
                                "/error",
                                "/error403",
                                "/seats/**",
                                "/index",
                                "/img/**",
                                "/img/bank/**",
                                "/login/**",
                                "/webjars/**",
                                "/customer-info",
                                "/favicon.ico",
                                "/submit-info",
                                "/book",
                                "/mobile-vnpay",
                                "/submitOrder",
                                "/user/submit",
                                "user/display",
                                "/submit-info",
                                "/step2/submit",
                                "/book/submit",
                                "/api/**", // Cho phép truy cập các API
                                "/admin/**",
                                "/payment",
                                "/user",
                                "/vnpay-payment-return",
                                "/createOrder",
                                "/success-booking",

                                "/admin/user-list",
                                "/admin",
                                "/admin/user",
                                "/admin/user/**",
                                "/admin/user/add",
                                "/admin/user/listUser",
                                "/admin/user/edit",
                                "/admin/user/findId",
                                "/admin/user/{id}",
                                "/admin/user/delete/{id}",
                                "/admin/user/update/{id}",

                                "/admin/trip-list",
                                "/admin-schedule/",
                                "/admin-schedule/trip",
                                "/admin-schedule/trip/add",
                                "/admin-schedule/**",

                                "/admin/station-list",
                                "/admin-station",
                                "/admin-station/station",
                                "/admin-station/station/listStation",
                                "/admin-station/station/add",
                                "/admin-station/station/update/{id}",
                                "/admin-station/station/delete/{id}",

                                "/admin-bus",
                                "/admin-bus/bus",
                                "/admin/bus-list",
                                "/admin-bus/bus/add",
                                "/admin-bus/bus/update/{id}",
                                "/admin-bus/bus/delete/{id}",

                                "/admin/company-list",
                                "/admin-company",
                                "/admin-company/company",
                                "/admin-company/**",

                                "/admin/seat-list",
                                "/admin-seat",
                                "/admin-seat/seat",

                                "/admin/ticket-list",
                                "/admin-ticket",
                                "/admin-ticket/ticket",

                                "/admin/stype-list",
                                "/admin-type",
                                "/admin-type/type",
                                "/admin-type/type/add",
                                "/admin-type/type/update/{id}",
                                "/admin-type/type/delete/{id}",

                                "/admin/statistical",
                                "/admin-payment",
                                "/admin-payment/payment-statistics",
                                "admin-payment/payment-statistics-data",

                                "/mobile-vnpay",
                                "/submitOrder",
                                "/admin-schebus/trip",
                                "../js/user.js",
                                "/mobile-vnpay",
                                "/submitOrder",
                                "/vnpay-payment-return",
                                "/login/profle",
                                "/schedules",
                                "/contact",
                                "/promotions"

                        ).permitAll()

                        // Các URL yêu cầu người dùng có quyền ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN") // chỉ cho phép người dùng có vai trò ADMIN truy cập vào /admin/**

                        // Các URL còn lại yêu cầu xác thực
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
