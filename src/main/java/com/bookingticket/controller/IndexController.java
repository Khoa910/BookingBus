package com.bookingticket.controller;


import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.service.BusScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BusScheduleService busScheduleService;

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

    @GetMapping()
    public String showSchedules(Model model) {
        List<ScheduleInfoRespond> schedules = busScheduleService.getAllSchedulesInfo();
        model.addAttribute("schedules", schedules);
        return "index"; // Trả về tên file HTML trong thư mục templates
    }

}