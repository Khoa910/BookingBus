package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class SeatMapperImpl implements SeatMapper {

    @Override
    public Seat toEntity(SeatRequest seatRequest) {
        if ( seatRequest == null ) {
            return null;
        }

        Seat seat = new Seat();

        return seat;
    }

    @Override
    public SeatRespond toRespond(Seat seat) {
        if ( seat == null ) {
            return null;
        }

        SeatRespond seatRespond = new SeatRespond();

        return seatRespond;
    }
}
