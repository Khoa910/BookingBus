package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class SeatTypeMapperImpl implements SeatTypeMapper {

    @Override
    public SeatType toEntity(SeatTypeRequest seatTypeRequest) {
        if ( seatTypeRequest == null ) {
            return null;
        }

        SeatType seatType = new SeatType();

        return seatType;
    }

    @Override
    public SeatTypeRespond toRespond(SeatType seatType) {
        if ( seatType == null ) {
            return null;
        }

        SeatTypeRespond seatTypeRespond = new SeatTypeRespond();

        return seatTypeRespond;
    }
}
