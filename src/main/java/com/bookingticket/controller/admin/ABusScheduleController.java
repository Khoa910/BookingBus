package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusStationRespond;
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

    @GetMapping("/trip")
    public String showBuses(Model model) {
        List<ScheduleInfoRespond>  schedules = AbusScheduleService.getAllSchedulesInfo();
            model.addAttribute("schedules", schedules);
        List<Bus> buses = AbusService.getAllBuses2();
        model.addAttribute("buses", buses);
        List<BusStationRespond> stations = AbusStationService.getAllBusStations();
        model.addAttribute("stations", stations);
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
        Logger logger = LoggerFactory.getLogger(getClass());

        try {
            // Log the incoming data for debugging
            logger.info("Received schedule data: " + scheduleData);

            // Retrieve schedule data
            String plate = (String) scheduleData.get("plate");
            Long plateIdLong = Long.parseLong(plate);
            String departure = (String) scheduleData.get("departure");
            Long departureIdLong = Long.parseLong(departure);
            String arrival = (String) scheduleData.get("arrival");
            Long arrivalIdLong = Long.parseLong(arrival);
            String start = (String) scheduleData.get("start");
            String end = (String) scheduleData.get("end");
            Object priceObj = scheduleData.get("price");

            // Kiểm tra dữ liệu price: Nếu là kiểu số (float), chuyển đổi sang BigDecimal
            BigDecimal priceValue = null;
            if (priceObj instanceof Number) {
                priceValue = new BigDecimal(((Number) priceObj).doubleValue());
            } else {
                response.put("message", "Giá không hợp lệ.");
                return ResponseEntity.badRequest().body(response);
            }

            // Chuyển đổi start và end thành LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.parse(start);
            LocalDateTime endDateTime = LocalDateTime.parse(end);

            // Create new schedule
            BusSchedule newSchedule = new BusSchedule();
            Bus bus = new Bus();
            bus.setId(plateIdLong);
            newSchedule.setBus(bus);

            BusStation busStation1 = new BusStation();
            busStation1.setId(departureIdLong);
            newSchedule.setDepartureStation(busStation1);

            BusStation busStation2 = new BusStation();
            busStation2.setId(arrivalIdLong);
            newSchedule.setArrivalStation(busStation2);

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
        } catch (Exception e) {
            logger.error("Lỗi khi thêm chuyến xe", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }



}
