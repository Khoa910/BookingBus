package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.SeatType;
import com.bookingticket.enumtype.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SeatMapperTest {

    private SeatMapper seatMapper;

    @BeforeEach
    public void setUp() {
        seatMapper = Mappers.getMapper(SeatMapper.class); // Initialize the mapper
    }

    @Test
    public void testToEntity() {
        // Tạo một SeatRequest giả
        SeatRequest seatRequest = new SeatRequest("A1", 1L, SeatStatus.AVAILABLE, "Seat 1", 2L);

        // Ánh xạ từ SeatRequest sang Seat entity
        Seat seat = seatMapper.toEntity(seatRequest);

        // Kiểm tra kết quả
        assertEquals(seatRequest.getId_seat(), seat.getId_seat());
        assertEquals(seatRequest.getSeat_name(), seat.getSeat_name());
        assertEquals(seatRequest.getStatus().toString(), seat.getStatus()); // Enum phải chuyển thành String
        assertEquals(seatRequest.getBus_id(), seat.getBus().getId()); // Kiểm tra bus_id ánh xạ đúng
        assertEquals(seatRequest.getSeat_type_id(), seat.getSeatType().getId()); // Kiểm tra seat_type_id ánh xạ đúng
    }

    @Test
    public void testToRespond() {
        // Tạo một Seat entity giả
        Bus bus = new Bus();
        bus.setId(1L);

        SeatType seatType = new SeatType();
        seatType.setId(2L);

        Seat seat = new Seat("A1", bus, "AVAILABLE", "Seat 1", seatType);

        // Ánh xạ từ Seat entity sang SeatRespond
        SeatRespond seatRespond = seatMapper.toRespond(seat);

        // Kiểm tra kết quả
        assertEquals(seat.getId_seat(), seatRespond.getId_seat());
        assertEquals(seat.getSeat_name(), seatRespond.getSeat_name());
        assertEquals(seat.getStatus(), seatRespond.getStatus());
        assertEquals(seat.getBus().getId(), seatRespond.getBus_id());
        assertEquals(seat.getSeatType().getId(), seatRespond.getSeat_type_id());
    }
}
