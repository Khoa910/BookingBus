package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.SeatType;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:26:41+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class SeatTypeMapperImpl implements SeatTypeMapper {

    @Override
    public SeatType toEntity(SeatTypeRequest seatTypeRequest) {
        if ( seatTypeRequest == null ) {
            return null;
        }

        SeatType seatType = new SeatType();

        seatType.setDescription( seatTypeRequest.getDescription() );
        seatType.setSeat_count( seatTypeRequest.getSeat_count() );

        return seatType;
    }

    @Override
    public SeatTypeRespond toRespond(SeatType seatType) {
        if ( seatType == null ) {
            return null;
        }

        SeatTypeRespond seatTypeRespond = new SeatTypeRespond();

        seatTypeRespond.setDescription( seatType.getDescription() );
        seatTypeRespond.setId( seatType.getId() );
        seatTypeRespond.setSeat_count( seatType.getSeat_count() );

        return seatTypeRespond;
    }
}
