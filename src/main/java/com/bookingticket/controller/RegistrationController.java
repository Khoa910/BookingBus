package com.bookingticket.controller;

import com.bookingticket.dto.request.RequestRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dangky")
public class RegistrationController {

    @PostMapping
    public String registerUser(@ModelAttribute("RequestRegister") RequestRegister form, Model model) {
        // Kiểm tra hoặc xử lý dữ liệu từ form
        System.out.println("Tên đăng nhập: " + form.getUsername());
        System.out.println("Mật khẩu: " + form.getPassword());
        System.out.println("Họ và tên: " + form.getFullName());
        System.out.println("Số điện thoại: " + form.getPhoneNumber());
        System.out.println("Email: " + form.getEmail());
        System.out.println("Địa chỉ: " + form.getAddress());
        model.addAttribute("message", "Đăng ký thành công!");
        return "success"; // Tên file HTML để hiển thị kết quả
    }
}
