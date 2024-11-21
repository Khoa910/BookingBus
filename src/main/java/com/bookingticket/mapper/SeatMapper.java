package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {


    @Mapping(target = "bus.id", source = "bus_id")
    @Mapping(target = "seatType.id", source = "seat_type_id")
    Seat toEntity(SeatRequest seatRequest);


    @Mapping(target = "bus_id", source = "bus.id")
    @Mapping(target = "seat_type_id", source = "seatType.id")
    SeatRespond toRespond(Seat seat);
}
