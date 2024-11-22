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
    public RequestRegister registerUser(@ModelAttribute RequestRegister form) {
        // In dữ liệu từ form (chỉ để kiểm tra, có thể bỏ khi triển khai thực tế)
        System.out.println("Tên đăng nhập: " + form.getUsername());
        System.out.println("Mật khẩu: " + form.getPassword());
        System.out.println("Họ và tên: " + form.getFullName());
        System.out.println("Số điện thoại: " + form.getPhoneNumber());
        System.out.println("Email: " + form.getEmail());
        System.out.println("Địa chỉ: " + form.getAddress());
        return form;
    }
}
