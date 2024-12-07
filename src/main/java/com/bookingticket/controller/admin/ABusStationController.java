package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.service.BusStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping( "/admin-station")
public class ABusStationController {
    private final BusStationService AbusStationService;
    private static final Logger logger = LoggerFactory.getLogger(ABusStationController.class);

    public ABusStationController(BusStationService AbusStationService) {
        this.AbusStationService = AbusStationService;
    }

    @GetMapping("/station")
    public String showBusStation(Model model) {
        List<BusStationRespond> stations = AbusStationService.getAllBusStations();
        model.addAttribute("stations", stations); // Đẩy danh sách user vào model
        return "admin/station-list"; // Trả về tên file HTML trong thư mục templates
    }

    @GetMapping("/station/listStation")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllStationJson() {
        List<BusStation> stations = AbusStationService.getAllBusStations2();
        List<Map<String, String>> stationList = new ArrayList<>();

        if (stations.isEmpty()) {
            logger.warn("No stations found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(stationList); // Trả về 204 nếu không có trạm
        } else {
            logger.info("Total stations: {}", stations.size());
            stations.forEach(station -> {
                // Tạo Map đại diện cho từng trạm xe
                Map<String, String> stationMap = new HashMap<>();
                stationMap.put("id", String.valueOf(station.getId()));
                stationMap.put("name", station.getName());
                stationMap.put("address", station.getAddress());
                stationList.add(stationMap); // Thêm vào danh sách
                logger.info("Station: {} - Name: {}, Address: {}", station.getId(), station.getName(), station.getAddress());
            });
            return ResponseEntity.ok(stationList); // Trả về danh sách các trạm với mã 200
        }
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

    @PutMapping("/station/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateStation(@PathVariable Integer id, @RequestBody Map<String, Object> StationData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String StationId = (String) StationData.get("id");
            Long stationIdLong = Long.parseLong(StationId);
            String stationName = (String) StationData.get("name");
            String stationAddress = (String) StationData.get("address");

            // Lấy Station từ ID
            Optional<BusStation> optionalStation = AbusStationService.getBusStationById2(stationIdLong);
            if (!optionalStation.isPresent()) {
                response.put("message", "Trạm xe không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            BusStation existingStation = optionalStation.get();
            existingStation.setName(stationName);
            existingStation.setAddress(stationAddress);
            // Gọi service để cập nhật trạm xe
            boolean updated = AbusStationService.updateBusStation2(existingStation);
            if (updated) {
                logger.info("Account with ID {} updated successfully.", existingStation.getId());
                response.put("message", "Trạm xe đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update account with ID {}.", existingStation.getId());
                response.put("message", "Cập nhật trạm xe thất bại!");
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


    @DeleteMapping("/station/delete/{id}")
    public ResponseEntity<String> deleteStation(@PathVariable("id") Long id) {
        try {
            AbusStationService.deleteBusStation(id);
            return ResponseEntity.ok("Station deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Station not found with id: " + id);
        }
    }

}
