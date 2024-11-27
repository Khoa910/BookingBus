package com.bookingticket.mapper;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.Seat;
import com.bookingticket.entity.SeatType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T00:19:38+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class SeatMapperImpl implements SeatMapper {

    @Override
    public Seat toEntity(SeatRequest seatRequest) {
        if ( seatRequest == null ) {
            return null;
        }

        Seat seat = new Seat();

        seat.setBus( seatRequestToBus( seatRequest ) );
        seat.setSeatType( seatRequestToSeatType( seatRequest ) );
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

        seatRespond.setBus_id( seatBusId( seat ) );
        seatRespond.setSeat_type_id( seatSeatTypeId( seat ) );
        seatRespond.setId_seat( seat.getId_seat() );
        seatRespond.setSeat_name( seat.getSeat_name() );
        seatRespond.setStatus( seat.getStatus() );

        return seatRespond;
    }

    protected Bus seatRequestToBus(SeatRequest seatRequest) {
        if ( seatRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        bus.setId( seatRequest.getBus_id() );

        return bus;
    }

    protected SeatType seatRequestToSeatType(SeatRequest seatRequest) {
        if ( seatRequest == null ) {
            return null;
        }

        SeatType seatType = new SeatType();

        seatType.setId( seatRequest.getSeat_type_id() );

        return seatType;
    }

    private Long seatBusId(Seat seat) {
        Bus bus = seat.getBus();
        if ( bus == null ) {
            return null;
        }
        return bus.getId();
    }

    private Long seatSeatTypeId(Seat seat) {
        SeatType seatType = seat.getSeatType();
        if ( seatType == null ) {
            return null;
        }
        return seatType.getId();
    }
}
