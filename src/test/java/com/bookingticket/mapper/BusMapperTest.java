package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.entity.Bus;
import com.bookingticket.enumtype.BusType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestConfig.class)
public class BusMapperTest {
    @Autowired
    private BusMapper busMapper;

    @Test
    public void testToEntity() {
        BusRequest request = new BusRequest("29A-12345", 34, BusType.SLEEPER, 1L, 2L, 3L);
        Bus bus = busMapper.toEntity(request);

        assertEquals("29A-12345", bus.getLicense_plate());
        assertEquals(34, bus.getSeat_count());
        assertEquals("SLEEPER", bus.getBus_type());
        assertEquals(1L, bus.getBus_company().getId());
    }
}
