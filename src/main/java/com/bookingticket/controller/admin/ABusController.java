package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping( "/admin-bus")
public class ABusController {
    private final BusService AbusService;

    public ABusController(BusService AbusService) {
        this.AbusService = AbusService;
    }

    @GetMapping("/trip")
    public String showBuses(Model model) {
        List<BusRespond> buses = AbusService.getAllBuses();
        model.addAttribute("buses", buses); // Đẩy danh sách user vào model
//        List<RoleRespond> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles); // Đẩy danh sách vai trò vào model
        return "/admin/trip"; // Trả về tên file HTML trong thư mục templates
    }
}
