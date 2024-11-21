package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:26:41+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class SeatMapperImpl implements SeatMapper {

    @Override
    public Seat toEntity(SeatRequest seatRequest) {
        if ( seatRequest == null ) {
            return null;
        }

        Seat seat = new Seat();

        seat.setId_seat( seatRequest.getId_seat() );
        seat.setSeat_name( seatRequest.getSeat_name() );
        if ( seatRequest.getStatus() != null ) {
            seat.setStatus( seatRequest.getStatus().name() );
        }

        return seat;
    }

    @Override
    public SeatRespond toRespond(Seat seat) {
        if ( seat == null ) {
            return null;
        }

        SeatRespond seatRespond = new SeatRespond();

        seatRespond.setId_seat( seat.getId_seat() );
        seatRespond.setSeat_name( seat.getSeat_name() );
        seatRespond.setStatus( seat.getStatus() );

        return seatRespond;
    }
}
