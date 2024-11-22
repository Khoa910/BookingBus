package com.bookingticket.controller;

import com.bookingticket.dto.request.RequestRegister;
import com.bookingticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dangky")
public class RegistrationController {
    @Autowired
    private UserService userService;


    @PostMapping
    public String registerUser(@ModelAttribute RequestRegister dto, Model model) {
        // Lưu người dùng
        boolean isRegistered = userService.registerUser(dto);

        if (isRegistered) {
            // Đăng ký thành công, chuyển hướng đến trang thành công
            model.addAttribute("message", "Đăng ký thành công!");
            return "success"; // Tên file HTML cho trang thành công (success.html)
        } else {
            // Đăng ký thất bại (ví dụ: trùng tên đăng nhập), trả về trang đăng ký với thông báo lỗi
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "register"; // Tên file HTML cho trang đăng ký (register.html)
        }
    }
}
