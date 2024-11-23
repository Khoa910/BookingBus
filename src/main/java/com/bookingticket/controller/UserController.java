package com.bookingticket.controller;

import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserRespond> users = userService.getAllUsers();
        model.addAttribute("users", users); // Đẩy danh sách user vào model
        return "users"; // Trả về tên file HTML trong thư mục templates
    }
}
