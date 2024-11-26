package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.service.BusScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/admin-schebus")
public class ABusScheduleController {
    private final BusScheduleService AbusScheduleService;

    public ABusScheduleController(BusScheduleService AbusScheduleService){
        this.AbusScheduleService = AbusScheduleService;
    }

//    @GetMapping("/trip")
//    public String showBusSchedules(Model model) {
//        List<BusScheduleRespond> busSchedules = busScheduleService.getAllBusSchedules();
//        model.addAttribute("busSchedules", busSchedules); // Đẩy danh sách user vào model
//        return "/admin/trip"; // Trả về tên file HTML trong thư mục templates
//    }
//    @GetMapping("/trip")
//    public String showSchedules(
//            @RequestParam(required = false) String departureStationName,
//            @RequestParam(required = false) String arrivalStationName,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
//            Model model) {
//
//        List<ScheduleInfoRespond> schedules;
//
//        if (departureStationName != null && arrivalStationName != null && departureTime != null) {
//            // Gọi hàm tìm kiếm lịch trình
//            List<BusSchedule> matchingSchedules = busScheduleService.findMatchingSchedules(departureStationName, arrivalStationName, departureTime);
//            // Chuyển đổi matchingSchedules thành ScheduleInfoRespond
//            schedules = matchingSchedules.stream()
//                    .map(schedule -> new ScheduleInfoRespond(
//                            schedule.getDepartureStation().getName(),
//                            schedule.getArrivalStation().getName(),
//                            schedule.getDeparture_time()))
//                    .collect(Collectors.toList());
//        } else {
//            // Hiển thị tất cả lịch trình nếu không có tham số tìm kiếm
//            schedules = busScheduleService.getAllSchedulesInfo();
//        }
//
//        model.addAttribute("schedules", schedules);
//        return "/admin/trip";
//    }
//    @GetMapping
//    public String showAllBusSchedules(Model model) {
//        List<BusScheduleDisplayRespond> schedules = AbusScheduleService.getAllDisplaySchedules();
//        model.addAttribute("schedules", schedules); // Đưa danh sách lịch trình vào model
//        return "/admin/trip"; // Trả về trang HTML để hiển thị
//    }
//
//    // Hiển thị lịch trình theo ID điểm đi
//    @GetMapping("/trip/{departureStationId}")
//    public String showBusSchedulesByDepartureStation(
//            @PathVariable Long departureStationId,
//            Model model) {
//        List<BusScheduleDisplayRespond> schedules = AbusScheduleService.getDisplaySchedulesByDepartureStationId(departureStationId);
//        model.addAttribute("schedules", schedules);
//        return "/admin/trip";
//    }

}
