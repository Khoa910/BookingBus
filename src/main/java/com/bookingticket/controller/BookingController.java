package com.bookingticket.controller;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.mapper.BusScheduleMapper;
import org.springframework.ui.Model;
import com.bookingticket.dto.request.BookingForm;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/book") // Đường dẫn gốc
public class BookingController {
    @Autowired
    BusScheduleService busScheduleService;
    @Autowired
    BusScheduleMapper busScheduleMapper;
    @PostMapping("/submit")
    public String findSchedules(@ModelAttribute BookingForm bookingForm, Model model) {
    // Lấy dữ liệu từ form
    String departureStationName = (bookingForm.getDeparture() == null || bookingForm.getDeparture().isEmpty())
                                  ? null
                                  : bookingForm.getDeparture();
    String arrivalStationName = (bookingForm.getArrival() == null || bookingForm.getArrival().isEmpty())
                                ? null
                                : bookingForm.getArrival();
    LocalDateTime departureTime = (bookingForm.getDepartureTime() == null || bookingForm.getDepartureTime().isEmpty())
                                  ? null
                                  : LocalDateTime.parse(bookingForm.getDepartureTime());

    // Gọi service để tìm bus schedule
    List<BusSchedule> schedules = busScheduleService.getSchedulesWithNames(departureStationName, arrivalStationName, departureTime);
    List<BusScheduleDisplayRespond> busScheduleDisplayResponds=new ArrayList<>();
    for (BusSchedule busSchedule : schedules) {
        busScheduleDisplayResponds.add(busScheduleMapper.toDisplayRespond(busSchedule));
    }
        if (busScheduleDisplayResponds.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy lịch trình nào phù hợp!");
        } else {
            model.addAttribute("schedules", busScheduleDisplayResponds);
        }
        System.out.println("Thông tin lịch trình gửi qua step 2"+ schedules.get(0).getPrice());
    return "user/step2";
}
}
