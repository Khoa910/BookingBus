package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.service.SeatTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin-type")
public class ASeatTypeController {
    private final SeatTypeService AseatTypeService;

    public ASeatTypeController(SeatTypeService AseatTypeService) {
        this.AseatTypeService = AseatTypeService;
    }

    @GetMapping("/type")
    public String showSeats(Model model) {
        List<SeatTypeRespond> seattypes = AseatTypeService.getAllSeatTypes();
        model.addAttribute("seattypes", seattypes);
        return "admin/stype-list";
    }

}
