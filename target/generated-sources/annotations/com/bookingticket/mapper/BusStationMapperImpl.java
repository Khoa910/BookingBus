package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-22T18:14:46+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class BusStationMapperImpl implements BusStationMapper {

    @Override
    public BusStation toEntity(BusStationRequest request) {
        if ( request == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setName( request.getName() );
        busStation.setAddress( request.getAddress() );

        return busStation;
    }

    @Override
    public BusStationRespond toRespond(BusStation busStation) {
        if ( busStation == null ) {
            return null;
        }

        BusStationRespond busStationRespond = new BusStationRespond();

        busStationRespond.setId( busStation.getId() );
        busStationRespond.setName( busStation.getName() );
        busStationRespond.setAddress( busStation.getAddress() );

        return busStationRespond;
    }
}
