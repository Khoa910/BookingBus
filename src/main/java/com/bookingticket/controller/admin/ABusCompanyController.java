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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin-company")
public class ABusCompanyController {
    private final BusCompanyService AbusCompanyService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ABusCompanyController(BusCompanyService AbusCompanyService) {
        this.AbusCompanyService = AbusCompanyService;
    }

    @GetMapping("/company")
    public String showBusCompany(Model model) {
        List<BusCompany> companies = AbusCompanyService.getAllBusCompanies2();
        model.addAttribute("companies", companies);
        return "admin/company-list";
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
                logger.info("Account with name {} added successfully.", name);
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

//    @PostMapping("/company/add")
//    @ResponseBody
//    public ResponseEntity<String> addBusCompany(@RequestBody BusCompany busCompany) {
//        AbusCompanyService.addBusCompany2(busCompany);
//        return ResponseEntity.ok("Công ty đã được thêm thành công.");
//    }
}
