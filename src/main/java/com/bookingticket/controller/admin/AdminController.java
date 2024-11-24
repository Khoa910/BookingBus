package com.bookingticket.controller.admin;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;

import com.bookingticket.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String admin() {
        return "/admin/index";
    }

//    @GetMapping("/user-list")
//    @ResponseBody
//    public ResponseEntity<List<UserRespond>> getAllAccountsJson() {
//        List<UserRespond> users = userService.getAllUsers();
//        if (users.isEmpty()) {
//            logger.warn("No accounts found.");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users); // Trả về 204 nếu không có tài khoản
//        } else {
//            logger.info("Total accounts: {}", users.size());
//            return ResponseEntity.ok(users); // Trả về danh sách tài khoản với mã 200
//        }
//    }

//    @GetMapping("/user-list")
//    public String getAllAccounts(Model model) {
//        List<UserRespond> users = userService.getAllUsers();
//        if (users.isEmpty()) {
//            logger.warn("No accounts found.");
//            model.addAttribute("message", "No accounts available.");
//        } else {
//            logger.info("Total accounts: {}", users.size());
//            model.addAttribute("users", users);
//        }
//        return "admin/user-list";
//    }

    @GetMapping("/user-list")
    public String showUsers(Model model) {
        List<UserRespond> users = userService.getAllUsers();
        model.addAttribute("users", users); // Đẩy danh sách user vào model
        return "admin/user-list"; // Trả về tên file HTML trong thư mục templates
    }
    @RequestMapping
    @PostMapping("user-list/add")
    public String addUser(@Valid @ModelAttribute("userRequest") UserRequest dto, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Looxibingling");
            return "error";
        }
        try {
            // Gọi service để thêm tài khoản
            UserRespond userRespond = userService.createUser(dto);
            // Thêm thành công, chuyển đến trang thành công
            model.addAttribute("message", "Thêm thành công! Người dùng: " + userRespond.getUsername());
            return "success"; // Tên file HTML cho trang thành công (success.html)
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            model.addAttribute("error", ex.getMessage());
            return "error";
        }

    }

    @PostMapping("user-list/update/{id}")
    public String updateUser(@PathVariable long id, @Valid @ModelAttribute("userRequest") UserRequest dto, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Looxibingling");
            return "error";
        }
        try {
            // Gọi service để sửa tài khoản
            UserRespond userRespond = userService.updateUser(id,dto);
            // Sửa thành công
            model.addAttribute("message", "Sửa thành công!");
            return "success"; // Tên file HTML cho trang thành công (success.html)
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            model.addAttribute("error", ex.getMessage());
            return "error"; // Tên file HTML cho trang đăng ký (register.html)
        }

    }
}