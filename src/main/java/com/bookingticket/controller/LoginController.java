package com.bookingticket.controller;

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

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

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
            return "Dữ liệu không hợp lệ";
        }
        try {
            // Gọi service để xử lý đăng nhập
            UserRespond userRespond = userService.loginUser(loginRequest);
            // Lưu thông tin vào session
            session.setAttribute("userId", userRespond.getId());
            session.setAttribute("username", userRespond.getFull_name());
            session.setAttribute("role", userRespond.getRole());

            return "index";
        } catch (RuntimeException ex) {
            // Xử lý lỗi đăng nhập
            model.addAttribute("error", ex.getMessage());
            return "Đăng nhập thất bại: " + ex.getMessage();
        }
    }



}