package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.BusSchedule;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:25:06+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class BusScheduleMapperImpl implements BusScheduleMapper {

    @Override
    public BusSchedule toEntity(BusScheduleRequest busScheduleRequest) {
        if ( busScheduleRequest == null ) {
            return null;
        }

        BusSchedule busSchedule = new BusSchedule();

        busSchedule.setArrival_time( busScheduleRequest.getArrival_time() );
        busSchedule.setDeparture_time( busScheduleRequest.getDeparture_time() );
        busSchedule.setPrice( busScheduleRequest.getPrice() );

        return busSchedule;
    }

    @Override
    public BusScheduleRespond toRespond(BusSchedule busSchedule) {
        if ( busSchedule == null ) {
            return null;
        }

        BusScheduleRespond busScheduleRespond = new BusScheduleRespond();

        busScheduleRespond.setArrival_time( busSchedule.getArrival_time() );
        busScheduleRespond.setDeparture_time( busSchedule.getDeparture_time() );
        busScheduleRespond.setId( busSchedule.getId() );
        busScheduleRespond.setPrice( busSchedule.getPrice() );

        return busScheduleRespond;
    }
}
