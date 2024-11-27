package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import com.bookingticket.service.BusStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping( "/admin-station")
public class ABusStationController {
    private final BusStationService AbusStationService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusStationController(BusStationService AbusStationService) {
        this.AbusStationService = AbusStationService;
    }

    @GetMapping("/station")
    public String showBusStation(Model model) {
        List<BusStationRespond> stations = AbusStationService.getAllBusStations();
        model.addAttribute("stations", stations); // Đẩy danh sách user vào model
        return "admin/station-list"; // Trả về tên file HTML trong thư mục templates
    }

    @PostMapping("/station/add")
    public ResponseEntity<Map<String, String>> addStation(@RequestBody Map<String, Object> stationData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ accountData
            String Name = (String) stationData.get("nameStation");
            String Address = (String) stationData.get("addressStation");

            // Tạo đối tượng User mới
            BusStation newStation = new BusStation();
            newStation.setName(Name);
            newStation.setAddress(Address);

            // Gọi service để thêm tài khoản
            boolean added = AbusStationService.addStation(newStation);
            if (added) {
                logger.info("Account with name {} added successfully.", Name);
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
}
