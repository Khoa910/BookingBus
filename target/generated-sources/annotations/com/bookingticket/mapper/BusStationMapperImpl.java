package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:25:06+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class BusStationMapperImpl implements BusStationMapper {

    @Override
    public BusStation toEntity(BusStationRequest busStationRequest) {
        if ( busStationRequest == null ) {
            return null;
        }

        BusStation busStation = new BusStation();

        busStation.setAddress( busStationRequest.getAddress() );
        busStation.setName( busStationRequest.getName() );

        return busStation;
    }

    @Override
    public BusStationRespond toRespond(BusStation busStation) {
        if ( busStation == null ) {
            return null;
        }

        BusStationRespond busStationRespond = new BusStationRespond();

        busStationRespond.setAddress( busStation.getAddress() );
        busStationRespond.setId( busStation.getId() );
        busStationRespond.setName( busStation.getName() );

        return busStationRespond;
    }
}
