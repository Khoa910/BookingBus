package com.bookingticket.controller.admin;

import com.bookingticket.entity.BusCompany;
import com.bookingticket.service.BusCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin-company")
public class ABusCompanyController {
    private final BusCompanyService AbusCompanyService;
    private static final Logger logger = LoggerFactory.getLogger(ABusCompanyController.class);

    public ABusCompanyController(BusCompanyService AbusCompanyService) {
        this.AbusCompanyService = AbusCompanyService;
    }

    @GetMapping("/company")
    public String showBusCompany(Model model) {
        List<BusCompany> companies = AbusCompanyService.getAllBusCompanies2();
        model.addAttribute("companies", companies);
        return "admin/company-list";
    }

    @GetMapping("/company/listCompany")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllCompanyJson() {
        List<BusCompany> companies = AbusCompanyService.getAllBusCompanies2();
        List<Map<String, String>> companyList = new ArrayList<>();

        if (companies.isEmpty()) {
            logger.warn("No stations found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(companyList); // Trả về 204 nếu không có trạm
        } else {
            logger.info("Total stations: {}", companies.size());
            companies.forEach(company -> {
                // Tạo Map đại diện cho từng trạm xe
                Map<String, String> companyMap = new HashMap<>();
                companyMap.put("idC", String.valueOf(company.getId()));
                companyMap.put("nameC", company.getName());
                companyMap.put("phone_numberC", company.getPhone_number());
                companyList.add(companyMap); // Thêm vào danh sách
                logger.info("ComapnyID: {} - Name: {}, Phone: {}", company.getId(), company.getName(), company.getPhone_number());
            });
            return ResponseEntity.ok(companyList); // Trả về danh sách các trạm với mã 200
        }
    }

    @PostMapping("/company/add")
    public ResponseEntity<Map<String, String>> addCompany(@RequestBody Map<String, Object> companyData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ companyData
            String name = (String) companyData.get("companyName");
            String phone = (String) companyData.get("companyPhone");

            // Tạo đối tượng User mới
            BusCompany newCompany = new BusCompany();
            newCompany.setName(name);
            newCompany.setPhone_number(phone);

            // Gọi service để thêm tài khoản
            boolean added = AbusCompanyService.addBusCompany3(newCompany);
            if (added) {
                logger.info("Company with name {} added successfully.", name);
                response.put("message", "Công ty đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Thêm công ty thất bại!"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

    @PutMapping("/company/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateCompany(@PathVariable Integer id, @RequestBody Map<String, Object> CompanyData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String CompanyId = (String) CompanyData.get("id");
            Long companyIdLong = Long.parseLong(CompanyId);
            String companyName = (String) CompanyData.get("name");
            String companyPhone = (String) CompanyData.get("phoneN");

            // Lấy Station từ ID
            Optional<BusCompany> optionalCompany = AbusCompanyService.getBusCompanyById2(companyIdLong);
            if (!optionalCompany.isPresent()) {
                response.put("message", "Công ty không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            BusCompany existingCompany = optionalCompany.get();
            existingCompany.setName(companyName);
            existingCompany.setPhone_number(companyPhone);
            // Gọi service để cập nhật trạm xe
            boolean updated = AbusCompanyService.updateBusCompany2(existingCompany);
            if (updated) {
                logger.info("Company with ID {} updated successfully.", existingCompany.getId());
                response.put("message", "Công ty đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update company with ID {}.", existingCompany.getId());
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


    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<String> deleteStation(@PathVariable("id") Long id) {
        try {
            AbusCompanyService.deleteBusCompany(id);
            return ResponseEntity.ok("Company deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Company not found with id: " + id);
        }
    }
}
