package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusStationMapper {

    BusStationMapper INSTANCE = Mappers.getMapper(BusStationMapper.class);

    // Ánh xạ từ BusStationRequest sang BusStation entity
    BusStation toEntity(BusStationRequest busStationRequest);

    // Ánh xạ từ BusStation entity sang BusStationRespond
    BusStationRespond toRespond(BusStation busStation);
}
