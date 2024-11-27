package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.BusStation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T01:34:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class BusScheduleMapperImpl implements BusScheduleMapper {

    @Override
    public BusSchedule toEntity(BusScheduleRequest request) {
        if ( request == null ) {
            return null;
        }

        BusSchedule busSchedule = new BusSchedule();

        busSchedule.setBus( busScheduleRequestToBus( request ) );
        busSchedule.setDepartureStation( busScheduleRequestToBusStation( request ) );
        busSchedule.setArrivalStation( busScheduleRequestToBusStation1( request ) );
        busSchedule.setDeparture_time( request.getDeparture_time() );
        busSchedule.setArrival_time( request.getArrival_time() );
        busSchedule.setPrice( request.getPrice() );

        return busSchedule;
    }

    @Override
    public BusScheduleRespond toRespond(BusSchedule busSchedule) {
        if ( busSchedule == null ) {
            return null;
        }

        BusScheduleRespond busScheduleRespond = new BusScheduleRespond();

        busScheduleRespond.setBus_id( busScheduleBusId( busSchedule ) );
        busScheduleRespond.setDepartureStation_id( busScheduleDepartureStationId( busSchedule ) );
        busScheduleRespond.setArrivalStation_id( busScheduleArrivalStationId( busSchedule ) );
        busScheduleRespond.setId( busSchedule.getId() );
        busScheduleRespond.setDepartureStation( busSchedule.getDepartureStation() );
        busScheduleRespond.setArrivalStation( busSchedule.getArrivalStation() );
        busScheduleRespond.setDeparture_time( busSchedule.getDeparture_time() );
        busScheduleRespond.setArrival_time( busSchedule.getArrival_time() );
        busScheduleRespond.setPrice( busSchedule.getPrice() );

        return busScheduleRespond;
    }

    @Override
    public BusScheduleDisplayRespond toDisplayRespond(BusSchedule busSchedule) {
        if ( busSchedule == null ) {
            return null;
        }

        BusScheduleDisplayRespond busScheduleDisplayRespond = new BusScheduleDisplayRespond();

        busScheduleDisplayRespond.setBus_id( busScheduleBusId( busSchedule ) );
        busScheduleDisplayRespond.setDepartureStation_id( busScheduleDepartureStationId( busSchedule ) );
        busScheduleDisplayRespond.setArrivalStation_id( busScheduleArrivalStationId( busSchedule ) );
        busScheduleDisplayRespond.setDepartureStationName( busScheduleDepartureStationName( busSchedule ) );
        busScheduleDisplayRespond.setArrivalStationName( busScheduleArrivalStationName( busSchedule ) );
        busScheduleDisplayRespond.setId( busSchedule.getId() );
        busScheduleDisplayRespond.setDeparture_time( busSchedule.getDeparture_time() );
        busScheduleDisplayRespond.setArrival_time( busSchedule.getArrival_time() );
        busScheduleDisplayRespond.setPrice( busSchedule.getPrice() );

        return busScheduleDisplayRespond;
    }

    protected Bus busScheduleRequestToBus(BusScheduleRequest busScheduleRequest) {
        if ( busScheduleRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        bus.setId( busScheduleRequest.getBus_id() );

        return bus;
    }

    protected BusStation busScheduleRequestToBusStation(BusScheduleRequest busScheduleRequest) {
        if ( busScheduleRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setId( busScheduleRequest.getDepartureStation_id() );

        return busStation;
    }

    protected BusStation busScheduleRequestToBusStation1(BusScheduleRequest busScheduleRequest) {
        if ( busScheduleRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setId( busScheduleRequest.getArrivalStation_id() );

        return busStation;
    }

    private Long busScheduleBusId(BusSchedule busSchedule) {
        Bus bus = busSchedule.getBus();
        if ( bus == null ) {
            return null;
        }
        return bus.getId();
    }

    private Long busScheduleDepartureStationId(BusSchedule busSchedule) {
        BusStation departureStation = busSchedule.getDepartureStation();
        if ( departureStation == null ) {
            return null;
        }
        return departureStation.getId();
    }

    private Long busScheduleArrivalStationId(BusSchedule busSchedule) {
        BusStation arrivalStation = busSchedule.getArrivalStation();
        if ( arrivalStation == null ) {
            return null;
        }
        return arrivalStation.getId();
    }

    private String busScheduleDepartureStationName(BusSchedule busSchedule) {
        BusStation departureStation = busSchedule.getDepartureStation();
        if ( departureStation == null ) {
            return null;
        }
        return departureStation.getName();
    }

    private String busScheduleArrivalStationName(BusSchedule busSchedule) {
        BusStation arrivalStation = busSchedule.getArrivalStation();
        if ( arrivalStation == null ) {
            return null;
        }
        return arrivalStation.getName();
    }
}
