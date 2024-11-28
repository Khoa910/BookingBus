package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.BusService;
import com.bookingticket.service.BusStationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private  BusScheduleService AbusScheduleService;
    private final BusService AbusService;
    private final BusStationService AbusStationService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusScheduleController(BusScheduleService AbusScheduleService, BusService AbusService, BusStationService AbusStationService) {
        this.AbusScheduleService = AbusScheduleService;
        this.AbusService = AbusService;
        this.AbusStationService = AbusStationService;
    }

//    @GetMapping("/trip")
//    public String showBuses(Model model) {
//        List<BusSchedule> schedules = AbusScheduleService.getAllDisplaySchedules2();
//            model.addAttribute("schedules", schedules);
//        return "admin/trip-list"; // Trả về tên file HTML trong thư mục templates
//    }

    @GetMapping("/trip")
    public String showSchedules(Model model) {
        List<ScheduleInfoRespond>  schedules = AbusScheduleService.getAllSchedulesInfo();
        model.addAttribute("schedules", schedules);
        List<Bus> buses = AbusService.getAllBuses2();
        model.addAttribute("buses", buses); // Đẩy danh sách user vào model
        List<BusStationRespond> stations = AbusStationService.getAllBusStations();
        model.addAttribute("stations", stations); // Đẩy danh sách user vào model
        return "admin/trip-list"; // Trả về tên file HTML trong thư mục templates
    }

    @GetMapping("/trip/listTrip")
    @ResponseBody
    public ResponseEntity<List<ScheduleInfoRespond>> getAllSchedulesJson() {
        List<ScheduleInfoRespond>  schedules = AbusScheduleService.getAllSchedulesInfo();
        if (schedules.isEmpty()) {
            logger.warn("No accounts found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(schedules); // Trả về 204 nếu không có tài khoản
        } else {
            logger.info("Total accounts: {}", schedules.size());
            schedules.forEach(user -> logger.info("User: {}", user)); // Ghi từng tài khoản
            return ResponseEntity.ok(schedules); // Trả về danh sách tài khoản với mã 200
        }
    }

    @PostMapping("/trip/add")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody Map<String, Object> scheduleData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ accountData
            String plate = (String) scheduleData.get("plate");
            String departure = (String) scheduleData.get("departure");
            String arrival = (String) scheduleData.get("arrival");
            String start = (String) scheduleData.get("start");
            String end = (String) scheduleData.get("end");
            BigDecimal price = (BigDecimal) scheduleData.get("price");

            // Tạo đối tượng BusSchedule mới
            BusSchedule newSchedule = new BusSchedule();
            newSchedule.getBus().setLicense_plate(plate);
            newSchedule.getDepartureStation().setName(departure);
            newSchedule.getArrivalStation().setName(arrival);
            newSchedule.setDeparture_time(LocalDateTime.parse(start));
            newSchedule.setArrival_time(LocalDateTime.parse(end));
            newSchedule.setPrice(price);

            // Gọi service để thêm chuyến xe
            boolean added = AbusScheduleService.addSchedule(newSchedule);
            if (added) {
                logger.info("Account with name {} added successfully.");
                response.put("message", "Tài khoản đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Thêm tài khoản thất bại!"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
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
