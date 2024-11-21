package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    // Ánh xạ từ SeatRequest sang Seat entity
    Seat toEntity(SeatRequest seatRequest);

    // Ánh xạ từ Seat entity sang SeatRespond
    SeatRespond toRespond(Seat seat);
}
