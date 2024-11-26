package com.bookingticket.controller;

import com.bookingticket.controller.admin.AdminController;
import com.bookingticket.dto.request.LoginRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.Role;
import com.bookingticket.repository.RoleRepository;
import com.bookingticket.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public String loginUser(
            @Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "Dữ liệu không hợp lệ"; // Trả về thông báo nếu có lỗi khi đăng nhập
        }

        try {
            // Gọi service để xử lý đăng nhập
            UserRespond userRespond = userService.loginUser(loginRequest);

            // Lưu thông tin vào session
            session.setAttribute("userId", userRespond.getId());
            session.setAttribute("username", userRespond.getFull_name());
            session.setAttribute("email", userRespond.getEmail());
            session.setAttribute("role", userRespond.getRole());
            session.setAttribute("fullname", userRespond.getFull_name()); // Lưu fullname vào session

            // Log thông tin người dùng
            logger.info("Logged in user: " + userRespond.getFull_name());
            logger.info("User role: " + userRespond.getRole().getName());

            // Kiểm tra role và điều hướng tương ứng
            if ("USER".equals(userRespond.getRole().getName())) { // Kiểm tra nếu role là USER
                return "index"; // Trả về trang index nếu role = USER
            } else if ("ADMIN".equals(userRespond.getRole().getName())) { // Kiểm tra nếu role là ADMIN
                return "mid-index"; // Trả về trang mid-index nếu role = ADMIN
            } else {
                model.addAttribute("error", "Bạn không có quyền truy cập.");
                return "login"; // Quay lại trang đăng nhập nếu role không hợp lệ
            }
        } catch (RuntimeException ex) {
            // Xử lý lỗi đăng nhập
            model.addAttribute("error", ex.getMessage());
            return "Đăng nhập thất bại: " + ex.getMessage();
        }
    }

}