package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.BusStation;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.BusService;
import com.bookingticket.service.BusStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping( "/admin-schedule")
public class ABusScheduleController {
    private final BusScheduleService AbusScheduleService;
    private final BusService AbusService;
    private final BusStationService AbusStationService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusScheduleController(BusScheduleService AbusScheduleService, BusService AbusService, BusStationService AbusStationService){
        this.AbusScheduleService = AbusScheduleService;
        this.AbusService = AbusService;
        this.AbusStationService = AbusStationService;
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

    @PostMapping("/trip/add")
    public ResponseEntity<Map<String, String>> addSchedule(@RequestBody Map<String, Object> scheduleData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Retrieve schedule data
            String plate = (String) scheduleData.get("plate");
            String departure = (String) scheduleData.get("departure");
            String arrival = (String) scheduleData.get("arrival");
            String start = (String) scheduleData.get("start");
            String end = (String) scheduleData.get("end");
            String price = (String) scheduleData.get("price");

            LocalDateTime startDateTime = LocalDateTime.parse(start);
            LocalDateTime endDateTime = LocalDateTime.parse(end);
            BigDecimal priceValue = new BigDecimal(price);

            // Fetch Bus by license plate
            Bus bus = AbusService.findByLicensePlate(plate);
            if (bus == null) {
                response.put("message", "Bus not found with plate: " + plate);
                return ResponseEntity.badRequest().body(response);
            }

            // Fetch departure and arrival stations by names
            BusStation departureStation = AbusStationService.findByName(departure);
            BusStation arrivalStation = AbusStationService.findByName(arrival);
            if (departureStation == null || arrivalStation == null) {
                response.put("message", "Departure or arrival station not found.");
                return ResponseEntity.badRequest().body(response);
            }

            // Create new schedule
            BusSchedule newSchedule = new BusSchedule();
            newSchedule.setBus(bus);
            newSchedule.setDepartureStation(departureStation);
            newSchedule.setArrivalStation(arrivalStation);
            newSchedule.setDeparture_time(startDateTime);
            newSchedule.setArrival_time(endDateTime);
            newSchedule.setPrice(priceValue);

            // Save schedule
            boolean added = AbusScheduleService.addSchedule(newSchedule);
            if (added) {
                response.put("message", "Chuyến xe đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Thêm chuyến xe thất bại!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

}
