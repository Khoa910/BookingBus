package com.bookingticket.controller;

import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

public class HomeController {

    @Autowired
    private BusScheduleService busScheduleService;

    @RequestMapping("/")
    public String index(Model model) {
        List<ScheduleInfoRespond> schedules = busScheduleService.getAllSchedulesInfo();
        model.addAttribute("schedules", schedules);
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register"; // Trả về tên file HTML 'register.html'
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/forgot-password")
    public String forgot() {
        return "forgot-password";
    }

    @RequestMapping("/schedules")
    public String BusSchedule(Model model) {
        List<ScheduleInfoRespond> schedules = busScheduleService.getAllSchedulesInfo();
        model.addAttribute("schedules", schedules);
        return "lichtrinh";
    }

    @RequestMapping("/contact")
    public String contact() { return "lienhe"; }

    @RequestMapping("/promotions")
    public String promotions() { return "tintuc"; }

//    @RequestMapping()
//    public String showSchedules(Model model) {
//        List<ScheduleInfoRespond> schedules = busScheduleService.getAllSchedulesInfo();
//        model.addAttribute("schedules", schedules);
//        return "index"; // Trả về tên file HTML trong thư mục templates
//    }

}
