package com.bookingticket.controller.admin;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.SeatType;
import com.bookingticket.service.BusCompanyService;
import com.bookingticket.service.BusService;
import com.bookingticket.service.SeatTypeService;
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
@RequestMapping( "/admin-bus")
public class ABusController {
    private final BusService AbusService;
    private final SeatTypeService AseatTypeService;
    private final BusCompanyService AbusCompanyService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusController(BusService AbusService, SeatTypeService AseatTypeService, BusCompanyService AbusCompanyService) {
        this.AbusService = AbusService;
        this.AseatTypeService = AseatTypeService;
        this.AbusCompanyService = AbusCompanyService;
    }

    @GetMapping("/bus")
    public String showBuses(Model model) {
        List<Bus> buses = AbusService.getAllBuses2();
        model.addAttribute("buses", buses); // Đẩy danh sách user vào model
        List<SeatType> seattypes = AseatTypeService.getAllSeatTypes2();
        model.addAttribute("seattypes", seattypes);
        List<BusCompany> companies = AbusCompanyService.getAllBusCompanies2();
        model.addAttribute("companies", companies);
        return "admin/bus-list"; // Trả về tên file HTML trong thư mục templates
    }

    @GetMapping("/bus/listBus")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllBusJson() {
        List<Bus> buses = AbusService.getAllBuses2();
        List<Map<String, String>> busList = new ArrayList<>();

        if (buses.isEmpty()) {
            logger.warn("No stations found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(busList); // Trả về 204 nếu không có trạm
        } else {
            logger.info("Total stations: {}", buses.size());
            buses.forEach(buss -> {
                // Tạo Map đại diện cho từng trạm xe
                Map<String, String> busMap = new HashMap<>();
                busMap.put("id", String.valueOf(buss.getId()));
                busMap.put("PlateBus", buss.getLicense_plate());
                busMap.put("SeatType", buss.getSeatType().getSeat_count().toString());
                busMap.put("BusType", buss.getBus_type());
                busMap.put("BCompany", buss.getBus_company().getName());
                busList.add(busMap); // Thêm vào danh sách
//                logger.info("Station: {} - Name: {}, Address: {}", buss.getId(), buss.getLicense_plate(), buss.getSeatType().getSeat_count().toString(), buss.getBus_type(), buss.getBus_company().getName());
            });
            return ResponseEntity.ok(busList); // Trả về danh sách các trạm với mã 200
        }
    }


    @PostMapping("/bus/add")
    public ResponseEntity<Map<String, String>> addBusses(@RequestBody Map<String, Object> bussData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ bussData
            String plate = (String) bussData.get("plate");
            String seatId = (String) bussData.get("selectedSeatId");
            Long seatIdLong = Long.parseLong(seatId);
            String Btype = (String) bussData.get("selectedBusType");
            String companyId = (String) bussData.get("selectedCompanyId");
            Long companyIdLong = Long.parseLong(companyId);

            // Tạo đối tượng Bus mới
            Bus newBuss = new Bus();
            newBuss.setLicense_plate(plate);
            newBuss.getSeatType().setId(seatIdLong);
            newBuss.setBus_type(Btype);
            newBuss.getBus_company().setId(companyIdLong);

            // Gọi service để thêm xe
            boolean added = AbusService.addBuss(newBuss);
            if (added) {
                logger.info("Bus with plate {} added successfully.", plate);
                response.put("message", "Xe đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Thêm xe thất bại!"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

    @PutMapping("/bus/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateBusses(@PathVariable Integer id, @RequestBody Map<String, Object> bussData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String BussId = (String) bussData.get("id");
            Long BussIdLong = Long.parseLong(BussId);
            String plate = (String) bussData.get("plate");
            String seatId = (String) bussData.get("selectedSeatId");
            Long seatIdLong = Long.parseLong(seatId);
            String Btype = (String) bussData.get("selectedBusType");
            String companyId = (String) bussData.get("selectedCompanyId");
            Long companyIdLong = Long.parseLong(companyId);

            // Lấy Station từ ID
            Optional<Bus> optionalBus = AbusService.getBusById2(BussIdLong);
            if (!optionalBus.isPresent()) {
                response.put("message", "Xe không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            Bus existingBus = optionalBus.get();
            existingBus.setLicense_plate(plate);
            existingBus.getSeatType().setId(seatIdLong);
            existingBus.setBus_type(Btype);
            existingBus.getBus_company().setId(companyIdLong);
            // Gọi service để cập nhật trạm xe
            boolean updated = AbusService.updateBus2(existingBus);
            if (updated) {
                logger.info("Account with ID {} updated successfully.", existingBus.getId());
                response.put("message", "Xe đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update account with ID {}.", existingBus.getId());
                response.put("message", "Cập nhật xe thất bại!");
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


    @DeleteMapping("/bus/delete/{id}")
    public ResponseEntity<String> deleteStation(@PathVariable("id") Long id) {
        try {
            AbusService.deleteBus(id);
            return ResponseEntity.ok("Station deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Station not found with id: " + id);
        }
    }
}
