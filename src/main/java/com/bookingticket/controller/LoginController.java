package com.bookingticket.controller;

import com.bookingticket.dto.request.LoginRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String loginUser(
            @Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "Dữ liệu không hợp lệ";
        }
        try {
            // Gọi service để xử lý đăng nhập
            UserRespond userRespond = userService.loginUser(loginRequest);

            // Lưu thông tin vào session
            session.setAttribute("userId", userRespond.getId());
            session.setAttribute("username", userRespond.getUsername());
            session.setAttribute("role", userRespond.getRole());

            return "Đăng nhập thành công! Chào mừng " + userRespond.getUsername();
        } catch (RuntimeException ex) {
            // Xử lý lỗi đăng nhập
            model.addAttribute("error", ex.getMessage());
            return "Đăng nhập thất bại: " + ex.getMessage();
        }
    }
}