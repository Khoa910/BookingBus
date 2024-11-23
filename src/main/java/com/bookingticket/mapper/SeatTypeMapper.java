package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatTypeMapper {


    @Mapping(target = "seat_count", source = "seat_count")
    @Mapping(target = "description", source = "description")
    SeatType toEntity(SeatTypeRequest seatTypeRequest);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "seat_count", source = "seat_count")
    @Mapping(target = "description", source = "description")
    SeatTypeRespond toRespond(SeatType seatType);
}
