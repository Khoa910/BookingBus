package com.bookingticket.controller.user;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class Step4Controller {
    @GetMapping("/user/step4")
    public String step4Page(Model model) {


        return "user/step4"; // Trả về file step4.html trong thư mục templates/user
    }

    @PostMapping("/user/payment")
    public String processPayment(@RequestParam("paymentMethod") String paymentMethod, Model model) {
        // Xử lý logic thanh toán

        return "user/payment-success"; // Trả về trang thanh toán thành công (nếu cần)
    }

}
