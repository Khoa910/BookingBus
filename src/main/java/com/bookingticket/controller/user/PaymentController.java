package com.bookingticket.controller.user;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.BusService;
import com.bookingticket.service.EmailService;
import com.bookingticket.service.TicketService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {
    @Autowired
    TicketService ticketService;
    @Autowired
    EmailService emailService;
    @Autowired
    BusScheduleRepository busScheduleRepository;
    @Autowired
    BusScheduleService busScheduleService;
    // Xử lý thanh toán khi người dùng gửi form
    @PostMapping("/payment")
    public String processPayment(
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("scheduleID") Long scheduleID,
            @RequestParam("seatIds") String seatIds,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("price") Double price,
            @RequestParam("terms") Boolean terms,
            @RequestParam("totalAmount") Double totalAmount,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("departureTime") String departureTime,
            HttpSession session,
            Model model) throws MessagingException {
            ArrayList<String> seats = new ArrayList<>();
            seats =  splitString(seatIds);
            System.out.println("Payment Method: " + paymentMethod);
            System.out.println("Schedule ID: " + scheduleID);
            for (String seat : seats) {
                System.out.println("Seat: " + seat);
            }
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Address: " + address);
            System.out.println("Phone: " + phone);
            System.out.println("Price: " + price);
            System.out.println("Terms: " + terms);
            System.out.println("Total Amount: " + totalAmount);
            if (paymentMethod.equals("cash-cash")) {
            TicketRequest ticketRequest = new TicketRequest();
            if(session.getAttribute("userId") == null) {
                System.out.println("User Not Logged In");
            emailService.sendBookingConfirmationEmail(email,name,scheduleID,seatIds,price,departureTime,address,phone);
            for ( String seat : seats) {
                ticketRequest.setUser_id(5L);
                ticketRequest.setBus_id(busScheduleRepository.findByIdQuery(scheduleID).getBus().getId());
                ticketRequest.setSeat_number(seat);
                ticketRequest.setPrice(BigDecimal.valueOf(price));
                ticketRequest.setDeparture_time(LocalDateTime.now());
                System.out.println(ticketService.bookTicket(ticketRequest));
            }
            }
            }
            return "error"; // Trang thông báo thất bại
        }
    public static ArrayList<String> splitString(String input) {
        // Loại bỏ dấu ngoặc vuông [] trước khi xử lý
        input = input.substring(1, input.length() - 1);  // Loại bỏ dấu ngoặc vuông

        // Tạo danh sách để chứa các chuỗi con
        ArrayList<String> list = new ArrayList<>();

        // Tách chuỗi đầu vào bằng dấu phẩy (`,`)
        String[] parts = input.split(",\\s*");

        // Thêm từng chuỗi con vào danh sách
        for (String part : parts) {
            list.add(part);
        }

        return list;
    }
}

