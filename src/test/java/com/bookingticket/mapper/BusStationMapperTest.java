package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BusStationMapperTest {

    private final BusStationMapper busStationMapper = Mappers.getMapper(BusStationMapper.class);

    @Test
    public void testToEntity() {
        // Tạo BusStationRequest
        BusStationRequest request = new BusStationRequest("Station A", "123 Street");

        // Chuyển đổi BusStationRequest thành BusStation entity
        BusStation busStation = busStationMapper.toEntity(request);

        // Kiểm tra rằng BusStation entity đã được tạo thành công và các trường được ánh xạ đúng
        assertNotNull(busStation);
        assertEquals("Station A", busStation.getName());
        assertEquals("123 Street", busStation.getAddress());
    }

    @Test
    public void testToRespond() {
        // Tạo BusStation entity
        BusStation busStation = new BusStation(1L, "Station A", "123 Street", null, null);

        // Chuyển đổi BusStation entity thành BusStationRespond
        BusStationRespond busStationRespond = busStationMapper.toRespond(busStation);

        // Kiểm tra rằng BusStationRespond đã được tạo thành công và các trường được ánh xạ đúng
        assertNotNull(busStationRespond);
        assertEquals(1L, busStationRespond.getId());
        assertEquals("Station A", busStationRespond.getName());
        assertEquals("123 Street", busStationRespond.getAddress());
    }
}
