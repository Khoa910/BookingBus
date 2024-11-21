package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BusMapperImpl implements BusMapper {

    @Override
    public Bus toEntity(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        return bus;
    }

    @Override
    public BusRespond toRespond(Bus bus) {
        if ( bus == null ) {
            return null;
        }

        BusRespond busRespond = new BusRespond();

        return busRespond;
    }
}
