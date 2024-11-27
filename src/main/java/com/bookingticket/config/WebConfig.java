//package com.bookingticket.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RoleInterceptor())
//                .addPathPatterns("/admin-station/**", "/admin-bus/**", "/admin/**") // Thêm các URL cần kiểm tra
//                .excludePathPatterns("/start-session", "/access-denied"); // Loại trừ URL không cần kiểm tra
//    }
//}
