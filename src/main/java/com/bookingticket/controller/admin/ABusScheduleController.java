package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.service.BusScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping( "/admin-schedule")
public class ABusScheduleController {
    private final BusScheduleService AbusScheduleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusScheduleController(BusScheduleService AbusScheduleService){
        this.AbusScheduleService = AbusScheduleService;
    }

    @GetMapping("/trip")
    public String showBuses(Model model) {
        List<BusSchedule> schedules = AbusScheduleService.getAllDisplaySchedules2();
            model.addAttribute("schedules", schedules);
        return "admin/trip-list"; // Trả về tên file HTML trong thư mục templates
    }

    @GetMapping("/trip/{id}")
    @ResponseBody
    public ResponseEntity<Optional<BusSchedule>> getAccountScheduleById(@PathVariable String id) {
        Optional<BusSchedule> busSche = AbusScheduleService.getBusScheduleById(id);
        if (busSche == null) {
            logger.warn("Account with ID {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404
        }
        return ResponseEntity.ok(busSche); // Trả về 200 với tài khoản
    }
}
