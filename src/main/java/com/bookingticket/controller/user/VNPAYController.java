package com.bookingticket.controller.user;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.repository.UserRepository;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.EmailService;
import com.bookingticket.service.TicketService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.bookingticket.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class VNPAYController {
    @Autowired
    private VNPAYService vnPayService;
    @Autowired
    TicketService ticketService;
    @Autowired
    EmailService emailService;
    @Autowired
    BusScheduleRepository busScheduleRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/mobile-vnpay")
    public String mobileVnPayPage(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("name"));
        model.addAttribute("total", session.getAttribute("total"));
        return "createOrder";
    }


    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model,HttpSession session) throws MessagingException {
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1){
            String paymentMethod = (String) session.getAttribute("paymentMethod");
            Long scheduleID = (Long) session.getAttribute("scheduleID");
            String seatIds = (String) session.getAttribute("seatIds");
            String name = (String) session.getAttribute("name");
            String email = (String) session.getAttribute("email");
            String address = (String) session.getAttribute("address");
            String phone = (String) session.getAttribute("phone");
            Double price = (Double) session.getAttribute("price");
            Boolean terms = (Boolean) session.getAttribute("terms");
            Double totalAmount = (Double) session.getAttribute("totalAmount");
            String departureDate = (String) session.getAttribute("departureDate");
            String departureTime = (String) session.getAttribute("departureTime");
            ArrayList<String> seats = new ArrayList<>();
            seats =  splitString(seatIds);
            TicketRequest ticketRequest = new TicketRequest();
            if(session.getAttribute("userId") != null) {
                System.out.println("User Not Logged In: " + session.getAttribute("userId"));
                emailService.sendBookingConfirmationEmail(email,name,scheduleID,seatIds,price,departureTime,address,phone);
                for ( String seat : seats) {
                    ticketRequest.setUser_id(21L);
                    ticketRequest.setBus_id(busScheduleRepository.findByIdQuery(scheduleID).getBus().getId());
                    ticketRequest.setSeat_number(seat);
                    ticketRequest.setPrice(BigDecimal.valueOf(price));
                    ticketRequest.setDeparture_time(LocalDateTime.now());
                    System.out.println(ticketService.bookTicket(ticketRequest));
                }
            }
            else{
                for ( String seat : seats) {
                    ticketRequest.setUser_id(21L);
                    ticketRequest.setBus_id(busScheduleRepository.findByIdQuery(scheduleID).getBus().getId());
                    ticketRequest.setSeat_number(seat);
                    ticketRequest.setPrice(BigDecimal.valueOf(price));
                    ticketRequest.setDeparture_time(LocalDateTime.now());
                    System.out.println(ticketService.bookTicket(ticketRequest));
                }
            }
        }
        session.setAttribute("paymentMethod", null);
        session.setAttribute("scheduleID", null);
        session.setAttribute("seatIds", null);
        session.setAttribute("name", null);
        session.setAttribute("email", null);
        session.setAttribute("address", null);
        session.setAttribute("phone", null);
        session.setAttribute("price", null);
        session.setAttribute("terms", null);
        session.setAttribute("totalAmount", null);
        session.setAttribute("departureDate", null);
        session.setAttribute("departureTime", null);
        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
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
