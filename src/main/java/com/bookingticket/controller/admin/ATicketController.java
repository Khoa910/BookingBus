package com.bookingticket.controller.admin;

import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Seat;
import com.bookingticket.entity.Ticket;
import com.bookingticket.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/admin-ticket")
public class ATicketController {
    private final TicketService AticketService;

    public ATicketController(TicketService AticketService) {
        this.AticketService = AticketService;
    }

    @GetMapping("/ticket")
    public String showTickets(Model model) {
        List<TicketRespond> tickets = AticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "admin/ticket-list";
    }

//@GetMapping("/ticket")
//public String showTickets(Model model) {
//    List<Ticket> tickets = AticketService.getAllTickets1();
//
//    // Tạo thời gian ngẫu nhiên trong khoảng 1 tuần trước cho từng vé
//    for (Ticket ticket : tickets) {
//        String randomDate = getRandomDateWithinLastWeek();
//        ticket.setRandomDate(randomDate); // Giả sử bạn thêm thuộc tính randomDate vào Ticket
//    }
//
//    model.addAttribute("tickets", tickets);
//    return "admin/ticket-list";
//}
//
//    // Hàm tạo thời gian ngẫu nhiên trong khoảng 1 tuần trước
//    private String getRandomDateWithinLastWeek() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime oneWeekAgo = now.minusWeeks(1);
//
//        long nowEpoch = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        long oneWeekAgoEpoch = oneWeekAgo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//
//        // Random epoch time giữa oneWeekAgo và now
//        long randomEpoch = ThreadLocalRandom.current().nextLong(oneWeekAgoEpoch, nowEpoch);
//
//        // Chuyển đổi epoch time thành LocalDateTime
//        LocalDateTime randomDate = LocalDateTime.ofInstant(
//                java.time.Instant.ofEpochMilli(randomEpoch),
//                ZoneId.systemDefault()
//        );
//
//        // Format ngày giờ
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return randomDate.format(formatter);
//    }
}
