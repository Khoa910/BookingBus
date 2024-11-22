package com.bookingticket.controller;

import org.springframework.ui.Model;
import com.bookingticket.dto.request.RequestRegister;
import com.bookingticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @GetMapping("/dangki")
    public String showRegistrationForm(Model model) {
        // Tạo DTO trống để binding dữ liệu từ form
        model.addAttribute("registerDTO", new RequestRegister());
        return "dangki";
    }

    @PostMapping("/dangki")
    public String registerUser(@ModelAttribute("registerDTO") RequestRegister registerDTO, Model model) {
        try {
            // Gọi service để lưu người dùng
            userService.register(registerDTO);
            // Thông báo thành công
            System.out.println(registerDTO.toString());
            model.addAttribute("successMessage", "Đăng ký thành công!");
            return "redirect:/dangnhap"; // Điều hướng tới trang đăng nhập
        } catch (Exception e) {
            // Thông báo lỗi
            model.addAttribute("errorMessage", "Đăng ký thất bại: " + e.getMessage());
            return "dangki";
        }
    }
}
