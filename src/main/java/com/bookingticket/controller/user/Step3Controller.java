package com.bookingticket.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class Step3Controller {
    @RequestMapping("/step3")
    public String step3() {
        return "user/step3";
    }
    @GetMapping("/customer-info")
    public String showCustomerInfo(HttpSession session, Model model) {
        // Kiểm tra session, nếu tồn tại, thêm vào model
        model.addAttribute("session", session);

        // Trả về template
        return "user/customer-info"; // Tệp HTML nằm trong thư mục templates/user
    }

//    @PostMapping("/submit-info")
//    public String submitCustomerInfo(CustomerInfoForm form, HttpSession session) {
//        // Lưu thông tin vào session
//        session.setAttribute("name", form.getName());
//        session.setAttribute("email", form.getEmail());
//
//        // Điều hướng sang bước tiếp theo
//        return "redirect:/payment";
//    }
}
