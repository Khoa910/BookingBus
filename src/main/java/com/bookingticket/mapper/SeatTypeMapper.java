package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeatTypeMapper {

    SeatTypeMapper INSTANCE = Mappers.getMapper(SeatTypeMapper.class);

    // Ánh xạ từ SeatTypeRequest sang SeatType entity
    SeatType toEntity(SeatTypeRequest seatTypeRequest);

    // Ánh xạ từ SeatType entity sang SeatTypeRespond
    SeatTypeRespond toRespond(SeatType seatType);
}
