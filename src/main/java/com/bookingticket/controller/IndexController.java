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
        if (username == null) {
            // Chuyển hướng về trang login nếu chưa đăng nhập
            return "redirect:/login";
        }
        return "index"; // Trả về template index.html
    }
}