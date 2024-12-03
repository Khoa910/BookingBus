package com.bookingticket.service;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.Seat;
import com.bookingticket.entity.Ticket;
import com.bookingticket.entity.User;
import com.bookingticket.enumtype.SeatStatus;
import com.bookingticket.enumtype.TicketStatus;
import com.bookingticket.mapper.TicketMapper;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.repository.SeatRepository;
import com.bookingticket.repository.TicketRepository;
import com.bookingticket.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final ValidationService validationService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, SeatRepository seatRepository, ValidationService validationService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.seatRepository = seatRepository;
        this.validationService = validationService;
    }

//    public List<TicketRespond> getAllTickets() {
//        List<Ticket> tickets = ticketRepository.findAll();
//        return tickets.stream()
//                .map(ticketMapper::toRespond)
//                .collect(Collectors.toList());
//    }

    public List<TicketRespond> getAllTickets() {
        // Lấy danh sách các vé từ cơ sở dữ liệu
        List<Ticket> tickets = ticketRepository.findAll();

        // Gán randomDate cho mỗi ticket và chuyển sang đối tượng TicketRespond
        return tickets.stream()
                .map(ticket -> {
                    TicketRespond respond = ticketMapper.toRespond(ticket);
                    respond.setRandomDate(getRandomDateWithinLastWeek());
                    return respond;
                })
                .collect(Collectors.toList());
    }

    public List<Ticket> getAllTickets1() {
        return ticketRepository.findAll();
    }

//    public List<Ticket> getAllTickets1() {
//        List<Ticket> tickets = ticketRepository.findAll();
//
//        // Tạo random date cho mỗi vé
//        for (Ticket ticket : tickets) {
//            String randomDate = getRandomDateWithinLastWeek();
//            ticket.setRandomDate(randomDate); // Gán giá trị randomDate cho từng ticket
//        }
//
//        return tickets;
//    }

    // Hàm tạo thời gian ngẫu nhiên trong khoảng 1 tuần trước
    private String getRandomDateWithinLastWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);

        long nowEpoch = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long oneWeekAgoEpoch = oneWeekAgo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Random epoch time giữa oneWeekAgo và now
        long randomEpoch = ThreadLocalRandom.current().nextLong(oneWeekAgoEpoch, nowEpoch);

        // Chuyển đổi epoch time thành LocalDateTime
        LocalDateTime randomDate = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(randomEpoch),
                ZoneId.systemDefault()
        );

        // Format ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return randomDate.format(formatter);
    }

    public TicketRespond getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.map(ticketMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public TicketRespond createTicket(TicketRequest ticketRequest) {
        Ticket ticket = ticketMapper.toEntity(ticketRequest);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toRespond(savedTicket);
    }

    public TicketRespond updateTicket(Long id, TicketRequest ticketRequest) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setSeat_number(ticketRequest.getSeat_number());
            ticket.setDeparture_time(ticketRequest.getDeparture_time());
            ticket.setPrice(ticketRequest.getPrice());

            String statusString = String.valueOf(ticketRequest.getStatus());
            if (statusString != null && !statusString.isEmpty()) {
                try {
                    ticket.setStatus(String.valueOf(TicketStatus.valueOf(statusString)));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid status value: " + statusString);
                }
            } else {
                throw new RuntimeException("Status is required.");
            }

            Ticket updatedTicket = ticketRepository.save(ticket);
            return ticketMapper.toRespond(updatedTicket);
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    public void deleteTicket(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            ticketRepository.delete(ticketOptional.get());
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    public String bookTicket(TicketRequest request) {
        // 1. Xác thực dữ liệu
        System.out.println(" hàm đặt vé");
        User user = validationService.validateUser(request.getUser_id());
        BusSchedule schedule = validationService.validateSchedule(request.getBus_id());

        Seat seat = null;
        SeatRequest seatRequest = new SeatRequest();

        // Kiểm tra xem người dùng có yêu cầu ghế hay không
        if (seatRequest.getId_seat() != null) {
            // Kiểm tra ghế có tồn tại và có sẵn không
            seat = seatRepository.findById(seatRequest.getId_seat())
                    .orElseThrow(() -> new RuntimeException("Ghế không tồn tại"));

            // Kiểm tra trạng thái ghế (chỉ có thể đặt ghế nếu trạng thái là AVAILABLE)
            if (!seat.getStatus().equals(SeatStatus.AVAILABLE)) {
                throw new RuntimeException("Ghế đã được đặt");
            }
        } else {
            // Nếu không có yêu cầu ghế, lấy tất cả ghế trống của chuyến xe
            List<Seat> availableSeats = seatRepository.findAllByStatusAndBus("AVAILABLE", schedule.getBus());
            if (availableSeats.isEmpty()) {
                throw new RuntimeException("Không còn ghế trống");
            }

            // Chọn ghế trống đầu tiên
            seat = availableSeats.get(0);
        }

        // 2. Đặt ghế và cập nhật trạng thái ghế
        seat.setStatus("BOOKED");
        seatRepository.save(seat);

        // 3. Đặt vé
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setBus(schedule.getBus());
        ticket.setSeat_number(seat.getId_seat());
        ticket.setDeparture_time(schedule.getDeparture_time());
        ticket.setPrice(schedule.getPrice());
        ticket.setStatus(TicketStatus.SOLD.name());
        System.out.println(ticket.toString());
        // Lưu vé vào cơ sở dữ liệu
        Ticket savedTicket = ticketRepository.save(ticket);

        // 4. Trả về thông tin vé đã đặt
        return "Book thành công";
    }
}