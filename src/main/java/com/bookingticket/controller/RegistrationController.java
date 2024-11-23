package com.bookingticket.controller;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(
            @Valid @ModelAttribute("userRequest") UserRequest dto, // Thêm @Valid để kích hoạt kiểm tra
            BindingResult bindingResult,                          // Để xử lý lỗi nếu có
            Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Looxibingling");
            return "error";
        }

        try {
            // Gọi service để đăng ký người dùng
            UserRespond userRespond = userService.registerUser(dto);

            // Đăng ký thành công, chuyển đến trang thành công
            model.addAttribute("message", "Đăng ký thành công! Người dùng: " + userRespond.getUsername());
            return "success"; // Tên file HTML cho trang thành công (success.html)
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            model.addAttribute("error", ex.getMessage());
            return "error"; // Tên file HTML cho trang đăng ký (register.html)
        }
    }
}
