package com.bookingticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/register")
    public String dangky() {
        return "register"; // Trả về tên file HTML 'register.html'
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/forgot-password")
    public String quenmatkhau() {
        return "forgot-password";
    }

}
