package com.bookingticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/dangky")
    public String dangky() {
        return "dangky"; // Trả về tên file HTML 'dangky.html'
    }
    @RequestMapping("/dangnhap")
    public String login() {
        return "dangnhap";
    }
    @RequestMapping("/quenmatkhau")
    public String quenmatkhau() {
        return "quenmatkhau";
    }

}
