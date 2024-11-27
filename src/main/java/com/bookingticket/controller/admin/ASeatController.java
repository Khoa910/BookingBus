package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin-seat")
public class ASeatController {
    private final SeatService AseatService;

    public ASeatController(SeatService AseatService) {
        this.AseatService = AseatService;
    }

    @GetMapping("/seat")
    public String showSeats(Model model) {
        List<SeatRespond> seats = AseatService.getAllSeats();
        model.addAttribute("seats", seats);
        return "admin/seat-list";
    }
}
