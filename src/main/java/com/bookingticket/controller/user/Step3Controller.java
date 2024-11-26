package com.bookingticket.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class Step3Controller {

    // Hiển thị trang Step 3 (có thể chứa thông tin lịch trình xe và ghế đã chọn)
    @GetMapping("/step3")
    public String step3(
            @RequestParam(required = false) Long busScheduleId,
            @RequestParam(required = false) List<String> selectedSeatIds,
            @RequestParam(required = false) Double price,
            Model model) {

        // Kiểm tra và thêm thông tin vào model (nếu có)
        if (busScheduleId != null) {
            model.addAttribute("busScheduleId", busScheduleId);
        }
        if (selectedSeatIds != null && !selectedSeatIds.isEmpty()) {
            model.addAttribute("selectedSeatIds", selectedSeatIds);
        }
        if (price != null) {
            model.addAttribute("price", price);
        }
        // Trả về trang user/step3 để hiển thị thông tin cho người dùng
        return "user/step3"; // Tên file HTML của trang bước 3
    }

    // Xử lý khi người dùng gửi thông tin khách hàng
    @PostMapping("/customer-info")
    public String showCustomerInfo(
            @RequestParam String customerName,
            @RequestParam String customerPhone,
            HttpSession session,
            Model model) {

        // Lưu thông tin khách hàng vào session
        session.setAttribute("customerName", customerName);
        session.setAttribute("customerPhone", customerPhone);

        // Thêm thông tin vào model để hiển thị
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerPhone", customerPhone);

        // Trả về trang thông tin khách hàng đã nhập
        return "user/customer-info"; // Tệp HTML nằm trong thư mục templates/user
    }
}
