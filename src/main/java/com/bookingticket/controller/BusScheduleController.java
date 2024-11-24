package com.bookingticket.controller;

import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.service.BusScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class BusScheduleController {

    private final BusScheduleService busScheduleService;

    public BusScheduleController(BusScheduleService busScheduleService) {
        this.busScheduleService = busScheduleService;
    }

    @GetMapping("/schedules")
    public List<ScheduleInfoRespond> getAllSchedulesInfo() {
        return busScheduleService.getAllSchedulesInfo();
    }
}