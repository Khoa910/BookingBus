package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.dto.respond.ScheduleInfoRespond;
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
@RequestMapping( "/admin-schebus")
public class ABusScheduleController {
    private final BusScheduleService AbusScheduleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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

    @GetMapping("/trip")
    public String showBuses(Model model) {
//        List<BusScheduleRespond> schedules = AbusScheduleService.getAllBusSchedules();
        List<ScheduleInfoRespond>  schedules = AbusScheduleService.getAllSchedulesInfo();
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
