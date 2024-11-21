package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.BusSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusScheduleMapper {

    BusScheduleMapper INSTANCE = Mappers.getMapper(BusScheduleMapper.class);

    // Ánh xạ từ BusScheduleRequest sang BusSchedule entity
//    @Mapping(target = "bus.id", source = "bus_id")
//    @Mapping(target = "departureStation.id", source = "departureStation_id")
//    @Mapping(target = "arrivalStation.id", source = "arrivalStation_id")
    BusSchedule toEntity(BusScheduleRequest busScheduleRequest);

    // Ánh xạ từ BusSchedule entity sang BusScheduleRespond
//    @Mapping(target = "bus_id", source = "bus.id")
//    @Mapping(target = "departureStation_id", source = "departureStation.id")
//    @Mapping(target = "arrivalStation_id", source = "arrivalStation.id")
    BusScheduleRespond toRespond(BusSchedule busSchedule);
}
