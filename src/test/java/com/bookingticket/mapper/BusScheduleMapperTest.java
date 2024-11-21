package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.BusStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestConfig.class)
public class BusScheduleMapperTest {

    @Autowired
    private BusScheduleMapper busScheduleMapper;

    @Test
    public void testToEntity() {
        // Tạo đối tượng BusScheduleRequest với dữ liệu giả lập
        BusScheduleRequest request = new BusScheduleRequest(
                1L, 2L, 3L,
                LocalDateTime.of(2024, 11, 25, 8, 0),
                LocalDateTime.of(2024, 11, 25, 12, 0),
                new BigDecimal("500000")
        );

        // Chuyển đổi BusScheduleRequest thành BusSchedule entity
        BusSchedule entity = busScheduleMapper.toEntity(request);

        // Kiểm tra các trường đã được ánh xạ chính xác
        assertNotNull(entity); // Đảm bảo entity không null
        assertEquals(1L, entity.getBus().getId()); // Kiểm tra ID của Bus
        assertEquals(2L, entity.getDepartureStation().getId()); // Kiểm tra ID của DepartureStation
        assertEquals(3L, entity.getArrivalStation().getId()); // Kiểm tra ID của ArrivalStation
        assertEquals(LocalDateTime.of(2024, 11, 25, 8, 0), entity.getDeparture_time()); // Kiểm tra thời gian khởi hành
        assertEquals(LocalDateTime.of(2024, 11, 25, 12, 0), entity.getArrival_time()); // Kiểm tra thời gian đến
        assertEquals(new BigDecimal("500000"), entity.getPrice()); // Kiểm tra giá vé
    }

    @Test
    public void testToRespond() {
        // Tạo đối tượng BusSchedule entity với dữ liệu giả lập
        Bus bus = new Bus();
        bus.setId(1L);

        BusStation departureStation = new BusStation();
        departureStation.setId(2L);

        BusStation arrivalStation = new BusStation();
        arrivalStation.setId(3L);

        BusSchedule entity = new BusSchedule(
                1L,
                bus,
                departureStation,
                arrivalStation,
                LocalDateTime.of(2024, 11, 25, 8, 0),
                LocalDateTime.of(2024, 11, 25, 12, 0),
                new BigDecimal("500000")
        );

        // Chuyển đổi BusSchedule entity thành BusScheduleRespond DTO
        BusScheduleRespond respond = busScheduleMapper.toRespond(entity);

        // Kiểm tra các trường đã được ánh xạ chính xác
        assertNotNull(respond); // Đảm bảo respond không null
        assertEquals(1L, respond.getId()); // Kiểm tra ID của Schedule
        assertEquals(1L, respond.getBus_id()); // Kiểm tra ID của Bus
        assertEquals(2L, respond.getDepartureStation_id()); // Kiểm tra ID của DepartureStation
        assertEquals(3L, respond.getArrivalStation_id()); // Kiểm tra ID của ArrivalStation
        assertEquals(LocalDateTime.of(2024, 11, 25, 8, 0), respond.getDeparture_time()); // Kiểm tra thời gian khởi hành
        assertEquals(LocalDateTime.of(2024, 11, 25, 12, 0), respond.getArrival_time()); // Kiểm tra thời gian đến
        assertEquals(new BigDecimal("500000"), respond.getPrice()); // Kiểm tra giá vé
    }
}
