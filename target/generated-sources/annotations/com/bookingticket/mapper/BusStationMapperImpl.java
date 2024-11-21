package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BusStationMapperImpl implements BusStationMapper {

    @Override
    public BusStation toEntity(BusStationRequest busStationRequest) {
        if ( busStationRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        return busStation;
    }

    @Override
    public BusStationRespond toRespond(BusStation busStation) {
        if ( busStation == null ) {
            return null;
        }

        BusStationRespond busStationRespond = new BusStationRespond();

        return busStationRespond;
    }
}
