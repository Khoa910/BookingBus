package com.bookingticket.controller.user;


import com.bookingticket.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Step4Controller {
    @Autowired
    public BusScheduleService busScheduleService;
    @GetMapping("/user/step4")
    public String step4Page(Model model) {


        return "user/step4"; // Trả về file step4.html trong thư mục templates/user
    }

    @PostMapping("/user/payment")
    public String processPayment(@RequestParam("paymentMethod") String paymentMethod, Model model) {
        // Xử lý logic thanh toán

        return "user/payment-success"; // Trả về trang thanh toán thành công (nếu cần)
    }
    @PostMapping("/submit-info")
    public String submitInfo(
            @RequestParam Long scheduleID,
            @RequestParam List<String> seatIds,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String phone,
            @RequestParam Double price,
            @RequestParam Boolean terms,
            Model model) {

//        <tr><td>Chuyến</td><td th:text="${trip}">TP HCM - Đà Lạt</td></tr>
//                            <tr><td>Ngày đi</td><td th:text="${departureDate}">10/10/2013</td></tr>
//                            <tr><td>Thời gian</td><td th:text="${departureTime}">07:00</td></tr>

        String trip = busScheduleService.getScheduleById(scheduleID).getDepartureStation().getName()+" - "+busScheduleService.getScheduleById(scheduleID).getArrivalStation().getName();
        LocalDateTime time = busScheduleService.getScheduleById(scheduleID).getDeparture_time();
        int year = time.getYear();
        int month = time.getMonthValue();  // Tháng (1-12)
        int dayOfMonth = time.getDayOfMonth();  // Ngày trong tháng (1-31)
        String departureDate = dayOfMonth+"/"+month+"/"+year;
        Double totalAmount = price * seatIds.size();  // Giả sử mỗi ghế có giá như nhau
        seatIds = seatIds.stream()
                .map(seat -> seat.replaceAll("[\\[\\]]", "")) // Loại bỏ dấu ngoặc vuông []
                .collect(Collectors.toList());
        String departureTime= time.getHour()+ " : " + time.getMinute();

        // In ra log để kiểm tra thông tin nhận được
        System.out.println("Received Schedule ID: " + scheduleID);
        System.out.println("Received Seat IDs: " + seatIds);
        System.out.println("Customer Name: " + name);
        System.out.println("Customer Email: " + email);
        System.out.println("Price: " + price);
        System.out.println("Customer Phone: " + phone);
        System.out.println("Terms Accepted: " + terms);

        // Lưu thông tin vào session hoặc database nếu cần
        model.addAttribute("trip", trip);
        model.addAttribute("departureDate", departureDate);
        model.addAttribute("departureTime", departureTime);
        model.addAttribute("scheduleID", scheduleID);
        model.addAttribute("seatIds", seatIds);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);
        model.addAttribute("terms", terms);
        model.addAttribute("price", price);
        model.addAttribute("totalAmount", totalAmount);  // Truyền giá trị tổng vào model
        // Chuyển đến bước tiếp theo (ví dụ: trang xác nhận)
        return "user/step4"; // Tên file HTML cho bước 4
    }


}
