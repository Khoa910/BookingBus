package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusStationMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    BusStation toEntity(BusStationRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    BusStationRespond toRespond(BusStation busStation);
}
