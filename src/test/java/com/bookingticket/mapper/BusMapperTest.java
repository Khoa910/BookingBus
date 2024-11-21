package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.SeatType;
import com.bookingticket.enumtype.BusType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestConfig.class)
public class BusMapperTest {

    @Autowired
    private BusMapper busMapper;

    @Test
    public void testToEntity() {
        // Tạo đối tượng BusRequest với các tham số cần thiết
        BusRequest request = new BusRequest("29A-12345", 1L, BusType.SLEEPER, 1L, 2L, 3L);

        // Chuyển đổi BusRequest thành Bus entity
        Bus bus = busMapper.toEntity(request);

        // Kiểm tra các trường trong entity Bus đã được ánh xạ đúng
        assertNotNull(bus);  // Kiểm tra nếu đối tượng Bus không phải là null
        assertEquals("29A-12345", bus.getLicense_plate());  // Kiểm tra biển số xe
        assertNotNull(bus.getSeatType());  // Kiểm tra nếu SeatType không phải là null
        assertEquals(1L, bus.getSeatType().getId());  // Kiểm tra ID của SeatType
        assertEquals(BusType.SLEEPER.toString(), bus.getBus_type());  // Kiểm tra kiểu xe (SLEEPER)
        assertNotNull(bus.getBus_company());  // Kiểm tra nếu BusCompany không phải là null
        assertEquals(1L, bus.getBus_company().getId());  // Kiểm tra ID của BusCompany
        assertNotNull(bus.getDepartureStation());  // Kiểm tra nếu DepartureStation không phải là null
        assertEquals(2L, bus.getDepartureStation().getId());  // Kiểm tra ID của Departure Station
        assertNotNull(bus.getArrivalStation());  // Kiểm tra nếu ArrivalStation không phải là null
        assertEquals(3L, bus.getArrivalStation().getId());  // Kiểm tra ID của Arrival Station
    }

    @Test
    public void testToRespond() {
        // Tạo đối tượng Bus với các tham số cần thiết
        Bus bus = new Bus();
        bus.setId(1L);
        bus.setLicense_plate("29A-12345");

        // Mocking the related entities (BusCompany, SeatType, etc.)
        SeatType seatType = new SeatType();
        seatType.setId(1L);
        bus.setSeatType(seatType);

        bus.setBus_type(BusType.SLEEPER.toString());

        BusCompany busCompany = new BusCompany();
        busCompany.setId(1L);
        bus.setBus_company(busCompany);

        BusStation departureStation = new BusStation();
        departureStation.setId(2L);
        bus.setDepartureStation(departureStation);

        BusStation arrivalStation = new BusStation();
        arrivalStation.setId(3L);
        bus.setArrivalStation(arrivalStation);

        // Chuyển đổi Bus entity thành BusRespond DTO
        BusRespond busRespond = busMapper.toRespond(bus);

        // Kiểm tra các trường trong BusRespond đã được ánh xạ đúng từ Bus entity
        assertNotNull(busRespond);  // Kiểm tra nếu BusRespond không phải là null
        assertEquals(1L, busRespond.getId());  // Kiểm tra ID của Bus
        assertEquals("29A-12345", busRespond.getLicense_plate());  // Kiểm tra biển số xe
        assertEquals(1L, busRespond.getSeat_type_id());  // Kiểm tra ID của SeatType
        assertEquals(BusType.SLEEPER.toString(), busRespond.getBus_type());  // Kiểm tra kiểu xe (SLEEPER)
        assertEquals(1L, busRespond.getBus_company_id());  // Kiểm tra ID của BusCompany
        assertEquals(2L, busRespond.getDepartureStation_id());  // Kiểm tra ID của DepartureStation
        assertEquals(3L, busRespond.getArrivalStation_id());  // Kiểm tra ID của ArrivalStation
    }
}
