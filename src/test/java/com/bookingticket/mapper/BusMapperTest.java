//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.BusRequest;
//import com.bookingticket.entity.Bus;
//import com.bookingticket.enumtype.BusType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Import(TestConfig.class)
//public class BusMapperTest {
//
//    @Autowired
//    private BusMapper busMapper;
//
//    @Test
//    public void testToEntity() {
//        // Tạo đối tượng BusRequest với các tham số cần thiết
//        BusRequest request = new BusRequest("29A-12345", 1L, BusType.SLEEPER, 1L, 2L, 3L);
//
//        // Chuyển đổi BusRequest thành Bus entity
//        Bus bus = busMapper.toEntity(request);
//
//        // Kiểm tra các trường trong entity Bus đã được ánh xạ đúng
//        assertEquals("29A-12345", bus.getLicense_plate());  // Kiểm tra biển số xe
//        assertEquals(1L, bus.getSeatType().getId());  // Kiểm tra ID của SeatType
//        assertEquals(BusType.SLEEPER, bus.getBus_type());  // Kiểm tra kiểu xe (SLEEPER)
//        assertEquals(1L, bus.getBus_company().getId());  // Kiểm tra ID của BusCompany
//        assertEquals(2L, bus.getDepartureStation().getId());  // Kiểm tra ID của Departure Station
//        assertEquals(3L, bus.getArrivalStation().getId());  // Kiểm tra ID của Arrival Station
//    }
//}
