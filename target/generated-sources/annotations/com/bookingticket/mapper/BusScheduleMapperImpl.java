package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.BusSchedule;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BusScheduleMapperImpl implements BusScheduleMapper {

    @Override
    public BusSchedule toEntity(BusScheduleRequest busScheduleRequest) {
        if ( busScheduleRequest == null ) {
            return null;
        }

        BusSchedule busSchedule = new BusSchedule();

        return busSchedule;
    }

    @Override
    public BusScheduleRespond toRespond(BusSchedule busSchedule) {
        if ( busSchedule == null ) {
            return null;
        }

        BusScheduleRespond busScheduleRespond = new BusScheduleRespond();

        return busScheduleRespond;
    }
}
