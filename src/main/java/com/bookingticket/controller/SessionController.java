package com.bookingticket.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class SessionController {

    @GetMapping("/start-session")
    public String startSession(HttpSession session) {
        session.setAttribute("userId", 12345L);
        session.setAttribute("username", "JohnDoe");
        session.setAttribute("role", "ADMIN"); // Lưu role vào session
        return "Session started!";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role"); // Lấy role từ session
        return "Username: " + username + ", Role: " + role;
    }
}
