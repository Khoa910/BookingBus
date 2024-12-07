package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.entity.*;
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
import java.util.*;

@Controller
@RequestMapping( "/admin-schedule")
public class ABusScheduleController {
    private final BusScheduleService AbusScheduleService;
    private final BusService AbusService;
    private final BusStationService AbusStationService;
    private static final Logger logger = LoggerFactory.getLogger(ABusScheduleController.class);

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
    public ResponseEntity<List<Map<String, String>>> getAllBusSchedules() {
        List<BusSchedule> schedules = AbusScheduleService.getAllBusSchedules2();
        List<Map<String, String>> scheduleList = new ArrayList<>();

        if (schedules.isEmpty()) {
            logger.warn("No bus schedules found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(scheduleList);
        }

        schedules.forEach(schedule -> {
            Map<String, String> scheduleMap = new HashMap<>();
            scheduleMap.put("idS", String.valueOf(schedule.getId()));
            scheduleMap.put("license_plateS", schedule.getBus().getLicense_plate());
            scheduleMap.put("departureS", schedule.getDepartureStation().getName());
            scheduleMap.put("arrivalS", schedule.getArrivalStation().getName());
            scheduleMap.put("departure_timeS", schedule.getDeparture_time().toString());
            scheduleMap.put("arrival_timeS", schedule.getArrival_time().toString());
            scheduleMap.put("priceS", schedule.getPrice().toString());

            scheduleList.add(scheduleMap);
        });

        return ResponseEntity.ok(scheduleList);
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

    @PutMapping("/trip/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateSchedule(@PathVariable Integer id, @RequestBody Map<String, Object> scheduleData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String SId = (String) scheduleData.get("idS");
            Long SIdLong = Long.parseLong(SId);
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

            LocalDateTime startDateTime = LocalDateTime.parse(start);
            LocalDateTime endDateTime = LocalDateTime.parse(end);

            // Lấy Station từ ID
            Optional<BusSchedule> optionalBusSchedule = AbusScheduleService.getBusScheduleById2(SIdLong);
            if (!optionalBusSchedule.isPresent()) {
                response.put("message", "Xe không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            BusSchedule existingBusSchedule = optionalBusSchedule.get();
            Bus bus = new Bus();
            bus.setId(plateIdLong);
            existingBusSchedule.setBus(bus);

            BusStation busStation1 = new BusStation();
            busStation1.setId(departureIdLong);
            existingBusSchedule.setDepartureStation(busStation1);

            BusStation busStation2 = new BusStation();
            busStation2.setId(arrivalIdLong);
            existingBusSchedule.setArrivalStation(busStation2);

            existingBusSchedule.setDeparture_time(startDateTime);
            existingBusSchedule.setArrival_time(endDateTime);
            existingBusSchedule.setPrice(priceValue);
            // Gọi service để cập nhật trạm xe
            boolean updated = AbusScheduleService.updateBusSchedule2(existingBusSchedule);
            if (updated) {
                logger.info("Account with ID {} updated successfully.", existingBusSchedule.getId());
                response.put("message", "Chuyến xe đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update account with ID {}.", existingBusSchedule.getId());
                response.put("message", "Cập nhật chuyến xe thất bại!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/trip/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("id") Long id) {
        try {
            AbusScheduleService.deleteBusSchedule(id);
            return ResponseEntity.ok("Schedule deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Schedule not found with id: " + id);
        }
    }


}
