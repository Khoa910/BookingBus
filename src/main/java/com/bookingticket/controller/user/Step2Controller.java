package com.bookingticket.controller.user;

import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import com.bookingticket.mapper.BusScheduleMapper;
import com.bookingticket.mapper.SeatMapper;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("selectedRoute")
@RequestMapping("/user")
public class Step2Controller {
    @Autowired
    private SeatService seatService;
    @Autowired
    BusScheduleService busScheduleService;
    @Autowired
    BusScheduleMapper busScheduleMapper;
    @Autowired
    SeatMapper seatMapper;

    // Hiển thị danh sách ghế dựa trên busId
    @GetMapping("/step2")
    public String step2(@RequestParam Long busId, Model model) {
        // Lấy danh sách ghế tầng trên và tầng dưới
        System.out.println("Có làm hàm này");
        List<SeatRespond>  upstairsSeatsdto =new ArrayList<>();
        List<SeatRespond>  downstairsSeatsdto =new ArrayList<>();
        for (Seat seat : seatService.getUpstairsSeats(busId)) {
            upstairsSeatsdto.add(seatMapper.toRespond(seat));
        }
        for (Seat seat : seatService.getDownstairsSeats(busId)) {
            downstairsSeatsdto.add(seatMapper.toRespond(seat));
        }
        upstairsSeatsdto = sortSeats(upstairsSeatsdto);
        downstairsSeatsdto = sortSeats(downstairsSeatsdto);
        for(SeatRespond seatRespond : upstairsSeatsdto){
            System.out.println(seatRespond.getSeat_name());
        }
        for(SeatRespond seatRespond : downstairsSeatsdto){
            System.out.println(seatRespond.getSeat_name());
        }

        model.addAttribute("upstairsSeats", upstairsSeatsdto);
        model.addAttribute("downstairsSeats", downstairsSeatsdto);

        model.addAttribute("upstairsRow1", upstairsSeatsdto.subList(0, 6));
        model.addAttribute("upstairsRow2", upstairsSeatsdto.subList(6, 11));
        model.addAttribute("upstairsRow3", upstairsSeatsdto.subList(11, 17));

        model.addAttribute("downstairsRow1", downstairsSeatsdto.subList(0, 6));
        model.addAttribute("downstairsRow2", downstairsSeatsdto.subList(6, 11));
        model.addAttribute("downstairsRow3", downstairsSeatsdto.subList(11, 17));
        // Trả về giao diện
        return "user/step2"; // Tên file HTML hiển thị ghế
    }

    public static List<SeatRespond> sortSeats(List<SeatRespond> seats) {
        return seats.stream()
                .sorted(Comparator.comparingInt(seat -> Integer.parseInt(seat.getSeat_name().substring(1)))) // Lấy số sau "B" và sắp xếp
                .collect(Collectors.toList());
    }

    // Xử lý chọn tuyến và tìm busId từ route
    @PostMapping("/display")
    public String selectRoute(@RequestParam(value = "route", required = false) Long routeId, Model model) {
        System.out.println("có làm hàm này submit");
        if (routeId == null) {
            model.addAttribute("message", "Bạn chưa chọn tuyến nào!");
            return "user/step2"; // Hiển thị lại giao diện hiện tại
        }

        BusScheduleDisplayRespond schedule = busScheduleMapper.toDisplayRespond(busScheduleService.getScheduleById(routeId));
        if (schedule == null) {
            model.addAttribute("message", "Không tìm thấy tuyến!");
            return "user/step2b";
        }

        // Lấy busId từ đối tượng và thêm vào model
        Long busId = schedule.getBus_id();
        model.addAttribute("selectedRoute", schedule); // Thêm thông tin lịch trình được chọn
        addSeatsToModel(busId, model); // Hàm thêm thông tin ghế

        return "user/step2b";
    }

    private void addSeatsToModel(Long busId, Model model) {
        List<SeatRespond> upstairsSeatsdto = seatService.getUpstairsSeats(busId).stream()
                .map(seatMapper::toRespond)
                .sorted(Comparator.comparingInt(seat -> Integer.parseInt(seat.getSeat_name().substring(1))))
                .collect(Collectors.toList());

        List<SeatRespond> downstairsSeatsdto = seatService.getDownstairsSeats(busId).stream()
                .map(seatMapper::toRespond)
                .sorted(Comparator.comparingInt(seat -> Integer.parseInt(seat.getSeat_name().substring(1))))
                .collect(Collectors.toList());

        model.addAttribute("upstairsSeats", upstairsSeatsdto);
        model.addAttribute("downstairsSeats", downstairsSeatsdto);

        model.addAttribute("upstairsRow1", upstairsSeatsdto.subList(0, 6));
        model.addAttribute("upstairsRow2", upstairsSeatsdto.subList(6, 11));
        model.addAttribute("upstairsRow3", upstairsSeatsdto.subList(11, 17));

        model.addAttribute("downstairsRow1", downstairsSeatsdto.subList(0, 6));
        model.addAttribute("downstairsRow2", downstairsSeatsdto.subList(6, 11));
        model.addAttribute("downstairsRow3", downstairsSeatsdto.subList(11, 17));
    }
    @PostMapping("/confirmSelection")
    public String confirmSelection(
            @RequestParam Long busScheduleId,
            @RequestParam List<String> seatIds,
            Model model) {
        // In ra log để kiểm tra dữ liệu nhận được
        System.out.println("Bus Schedule ID: " + busScheduleId);
        System.out.println("Selected Seat IDs: " + seatIds);

        // Xử lý dữ liệu - Lưu thông tin hoặc truyền đến giao diện tiếp theo
        model.addAttribute("busScheduleId", busScheduleId);
        model.addAttribute("selectedSeatIds", seatIds);

        // Chuyển sang bước tiếp theo, ví dụ: xác nhận thông tin
        return "user/step3"; // Tên file HTML tiếp theo
    }
}
