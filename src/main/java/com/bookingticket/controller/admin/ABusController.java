package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
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

//    @GetMapping("/bus")
//    public String showBuses(Model model) {
//        List<BusRespond> buses = AbusService.getAllBuses();
//        model.addAttribute("buses", buses); // Đẩy danh sách user vào model
//        return "admin/bus-list"; // Trả về tên file HTML trong thư mục templates
//    }

    @GetMapping("/bus")
    public String showBuses(Model model) {
        List<Bus> buses = AbusService.getAllBuses2();
        model.addAttribute("buses", buses); // Đẩy danh sách user vào model
        return "admin/bus-list"; // Trả về tên file HTML trong thư mục templates
    }
}
