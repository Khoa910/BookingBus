package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import com.bookingticket.service.SeatTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin-type")
public class ASeatTypeController {
    private final SeatTypeService AseatTypeService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ASeatTypeController(SeatTypeService AseatTypeService) {
        this.AseatTypeService = AseatTypeService;
    }

    @GetMapping("/type")
    public String showSeats(Model model) {
        List<SeatTypeRespond> seattypes = AseatTypeService.getAllSeatTypes();
        model.addAttribute("seattypes", seattypes);
        return "admin/stype-list";
    }

    @GetMapping("/type/listType")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllTypeJson() {
        List<SeatType> types = AseatTypeService.getAllSeatTypes2();
        List<Map<String, String>> companyList = new ArrayList<>();

        if (types.isEmpty()) {
            logger.warn("No stations found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(companyList); // Trả về 204 nếu không có trạm
        } else {
            logger.info("Total stations: {}", types.size());
            types.forEach(type -> {
                // Tạo Map đại diện cho từng trạm xe
                Map<String, String> typeMap = new HashMap<>();
                typeMap.put("id", String.valueOf(type.getId()));
                typeMap.put("SeatCount", String.valueOf(type.getSeat_count()));
                typeMap.put("describe", type.getDescription());
                companyList.add(typeMap); // Thêm vào danh sách
                logger.info("ComapnyID: {} - Name: {}, Phone: {}", type.getId(), type.getSeat_count(), type.getDescription());
            });
            return ResponseEntity.ok(companyList); // Trả về danh sách các trạm với mã 200
        }
    }

    @PostMapping("/type/add")
    public ResponseEntity<Map<String, String>> addType(@RequestBody Map<String, Object> typeData) {
        Map<String, String> response = new HashMap<>();
        try {
            String countStr = (String) typeData.get("count");
            String dess = (String) typeData.get("des");

            Long countLong = Long.parseLong(countStr);

            if (countStr == null || countStr.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Số lượng không được để trống"));
            }

            // Log the data to ensure correct values
            System.out.println("Received count: " + countLong);
            System.out.println("Received description: " + dess);

            SeatType newSeat = new SeatType();
            newSeat.setSeat_count(countLong);
            newSeat.setDescription(dess);

            // Log SeatType object before saving it
            System.out.println("SeatType to add: " + newSeat);

            boolean added = AseatTypeService.addSeatType2(newSeat);

            // Log the result of addSeatType
            System.out.println("SeatType added: " + added);

            if (added) {
                response.put("message", "Công ty đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Thêm công ty thất bại!"));
            }
        } catch (Exception e) {
            e.printStackTrace();  // This will print stack trace in the console, helpful for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }



    @PutMapping("/type/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateType(@PathVariable Integer id, @RequestBody Map<String, Object> typeData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String TypeId = (String) typeData.get("id");
            Long typeIdLong = Long.parseLong(TypeId);
            String Count = (String) typeData.get("count");
            Long CountLong = Long.parseLong(Count);
            String dess = (String) typeData.get("des");

            // Lấy Station từ ID
            Optional<SeatType> optionalType = AseatTypeService.getSeatTypeById2(typeIdLong);
            if (!optionalType.isPresent()) {
                response.put("message", "Công ty không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            SeatType existingType = optionalType.get();
            existingType.setSeat_count(CountLong);
            existingType.setDescription(dess);
            // Gọi service để cập nhật trạm xe
            boolean updated = AseatTypeService.updateSeatType2(existingType);
            if (updated) {
                logger.info("Company with ID {} updated successfully.", existingType.getId());
                response.put("message", "Công ty đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update company with ID {}.", existingType.getId());
                response.put("message", "Cập nhật công ty thất bại!");
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


    @DeleteMapping("/type/delete/{id}")
    public ResponseEntity<String> deleteType(@PathVariable("id") Long id) {
        try {
            AseatTypeService.deleteSeatType(id);
            return ResponseEntity.ok("Company deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Company not found with id: " + id);
        }
    }

}
