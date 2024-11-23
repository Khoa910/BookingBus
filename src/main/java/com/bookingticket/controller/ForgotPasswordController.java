package com.bookingticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookingticket.service.UserService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    /**
     * Hiển thị trang quên mật khẩu
     */
    @GetMapping("/reset-password")
    public String showForgotPasswordPage(Model model) {
        // Có thể thêm các dữ liệu cần thiết vào model nếu cần
        return "quenmatkhau"; // Tên file HTML trong thư mục templates
    }

    /**
     * Xử lý logic khi người dùng gửi yêu cầu quên mật khẩu
     */
    @PostMapping("/reset-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            // Gọi service xử lý quên mật khẩu
            userService.forgotPassword(email);

            // Nếu thành công, thông báo cho người dùng
            model.addAttribute("successMessage", "Mật khẩu mới đã được gửi đến email của bạn.");
        } catch (RuntimeException e) {
            // Nếu gặp lỗi, trả thông báo lỗi về giao diện
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "quenmatkhau"; // Quay lại trang quên mật khẩu
    }
}