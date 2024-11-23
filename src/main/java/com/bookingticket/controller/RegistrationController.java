package com.bookingticket.controller;

import com.bookingticket.dto.request.RequestRegister;
import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
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
    public String registerUser(@ModelAttribute UserRequest dto, Model model) {
        try {
            // Gọi service để đăng ký người dùng
            UserRespond userRespond = userService.registerUser(dto);

            // Đăng ký thành công, chuyển hướng đến trang thành công
            model.addAttribute("message", "Đăng ký thành công! Người dùng: " + userRespond.getUsername());
            return "success"; // Tên file HTML cho trang thành công (success.html)
        } catch (RuntimeException ex) {
            // Đăng ký thất bại, trả về trang đăng ký với thông báo lỗi
            model.addAttribute("error", ex.getMessage());
            return "register"; // Tên file HTML cho trang đăng ký (register.html)
        }
    }

}
