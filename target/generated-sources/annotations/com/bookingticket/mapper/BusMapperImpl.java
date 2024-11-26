package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.SeatType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T21:48:22+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class BusMapperImpl implements BusMapper {

    @Override
    public Bus toEntity(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        bus.setSeatType( busRequestToSeatType( busRequest ) );
        bus.setBus_company( busRequestToBusCompany( busRequest ) );
        bus.setDepartureStation( busRequestToBusStation( busRequest ) );
        bus.setArrivalStation( busRequestToBusStation1( busRequest ) );
        bus.setLicense_plate( busRequest.getLicense_plate() );
        if ( busRequest.getBus_type() != null ) {
            bus.setBus_type( busRequest.getBus_type().name() );
        }

        return bus;
    }

    @Override
    public BusRespond toRespond(Bus bus) {
        if ( bus == null ) {
            return null;
        }

        BusRespond busRespond = new BusRespond();

        busRespond.setId( bus.getId() );
        busRespond.setLicense_plate( bus.getLicense_plate() );
        busRespond.setSeat_type_id( busSeatTypeId( bus ) );
        busRespond.setBus_type( bus.getBus_type() );
        busRespond.setBus_company_id( busBus_companyId( bus ) );
        busRespond.setDepartureStation_id( busDepartureStationId( bus ) );
        busRespond.setArrivalStation_id( busArrivalStationId( bus ) );

        return busRespond;
    }

    protected SeatType busRequestToSeatType(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        SeatType seatType = new SeatType();

        seatType.setId( busRequest.getSeat_type_id() );

        return seatType;
    }

    protected BusCompany busRequestToBusCompany(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        BusCompany busCompany = new BusCompany();

        busCompany.setId( busRequest.getBus_company_id() );

        return busCompany;
    }

    protected BusStation busRequestToBusStation(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setId( busRequest.getDepartureStation_id() );

        return busStation;
    }

    protected BusStation busRequestToBusStation1(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setId( busRequest.getArrivalStation_id() );

        return busStation;
    }

    private Long busSeatTypeId(Bus bus) {
        SeatType seatType = bus.getSeatType();
        if ( seatType == null ) {
            return null;
        }
        return seatType.getId();
    }

    private Long busBus_companyId(Bus bus) {
        BusCompany bus_company = bus.getBus_company();
        if ( bus_company == null ) {
            return null;
        }
        return bus_company.getId();
    }

    private Long busDepartureStationId(Bus bus) {
        BusStation departureStation = bus.getDepartureStation();
        if ( departureStation == null ) {
            return null;
        }
        return departureStation.getId();
    }

    private Long busArrivalStationId(Bus bus) {
        BusStation arrivalStation = bus.getArrivalStation();
        if ( arrivalStation == null ) {
            return null;
        }
        return arrivalStation.getId();
    }
}
