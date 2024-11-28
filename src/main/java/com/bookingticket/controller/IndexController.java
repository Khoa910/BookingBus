package com.bookingticket.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String indexPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (username == null) {
            // Chuyển hướng về trang login nếu chưa đăng nhập
            return "redirect:/login";
        }
        else{
            // Kiểm tra nếu role là ADMIN, nếu không chuyển hướng về trang khác
            if ("ADMIN".equals(role)) {
                return "admin/index"; // Trả về trang admin nếu là ADMIN
            } else {
                return "index"; // Chuyển hướng đến trang index nếu không phải ADMIN
            }
        }
    }
}