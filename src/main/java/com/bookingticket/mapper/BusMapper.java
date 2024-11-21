package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusMapper {

    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    @Mappings({
            @Mapping(source = "license_plate", target = "license_plate"),
            @Mapping(source = "seat_type_id", target = "seatType.id"),
            @Mapping(source = "bus_type", target = "bus_type"),
            @Mapping(source = "bus_company_id", target = "bus_company.id"),
            @Mapping(source = "departureStation_id", target = "departureStation.id"),
            @Mapping(source = "arrivalStation_id", target = "arrivalStation.id")
    })
    Bus toEntity(BusRequest busRequest);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "license_plate", target = "license_plate"),
            @Mapping(source = "seatType.id", target = "seat_type_id"),
            @Mapping(source = "bus_type", target = "bus_type"),
            @Mapping(source = "bus_company.id", target = "bus_company_id"),
            @Mapping(source = "departureStation.id", target = "departureStation_id"),
            @Mapping(source = "arrivalStation.id", target = "arrivalStation_id")
    })
    BusRespond toRespond(Bus bus);

}
