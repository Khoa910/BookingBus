package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-23T22:05:16+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class SeatTypeMapperImpl implements SeatTypeMapper {

    @Override
    public SeatType toEntity(SeatTypeRequest seatTypeRequest) {
        if ( seatTypeRequest == null ) {
            return null;
        }

        SeatType seatType = new SeatType();

        seatType.setSeat_count( seatTypeRequest.getSeat_count() );
        seatType.setDescription( seatTypeRequest.getDescription() );

        return seatType;
    }

    @Override
    public SeatTypeRespond toRespond(SeatType seatType) {
        if ( seatType == null ) {
            return null;
        }

        SeatTypeRespond seatTypeRespond = new SeatTypeRespond();

        seatTypeRespond.setId( seatType.getId() );
        seatTypeRespond.setSeat_count( seatType.getSeat_count() );
        seatTypeRespond.setDescription( seatType.getDescription() );

        return seatTypeRespond;
    }
}
