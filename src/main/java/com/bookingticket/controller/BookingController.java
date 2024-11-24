package com.bookingticket.controller;

import ch.qos.logback.core.model.Model;
import com.bookingticket.dto.request.BookingForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/book") // Đường dẫn gốc
public class BookingController {

    @PostMapping //
    public String handleBooking(@ModelAttribute BookingForm bookingRequest, Model model) {
        // Xử lý logic với dữ liệu từ form
        System.out.println("Điểm đi: " + bookingRequest.getDeparture());
        System.out.println("Điểm đến: " + bookingRequest.getArrival());
        System.out.println("Thời gian khởi hành: " + bookingRequest.getDepartureTime());
        System.out.println("Số lượng vé: " + bookingRequest.getQuantity());


        // Redirect hoặc render một view
        return "user/step2"; // Tên view trả về
    }
}
