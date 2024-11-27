//package com.bookingticket.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class RoleInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String role = (String) request.getSession().getAttribute("role");
//        String requestURI = request.getRequestURI();
//
//        // Kiểm tra quyền dựa trên URL
//        if (requestURI.startsWith("/admin-station") && !role.equals("ADMIN")) {
//            response.sendRedirect("/access-denied");
//            return false;
//        } else if (requestURI.startsWith("/admin-bus") && !role.equals("BUS_MANAGER")) {
//            response.sendRedirect("/access-denied");
//            return false;
//        } else if (requestURI.startsWith("/admin") && !role.equals("SUPER_ADMIN")) {
//            response.sendRedirect("/access-denied");
//            return false;
//        }
//
//        return true; // Tiếp tục nếu quyền hợp lệ
//    }
//}
//
