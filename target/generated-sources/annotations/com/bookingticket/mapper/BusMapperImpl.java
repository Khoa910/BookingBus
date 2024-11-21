package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:25:06+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class BusMapperImpl implements BusMapper {

    @Override
    public Bus toEntity(BusRequest busRequest) {
        if ( busRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        if ( busRequest.getBus_type() != null ) {
            bus.setBus_type( busRequest.getBus_type().name() );
        }
        bus.setLicense_plate( busRequest.getLicense_plate() );

        return bus;
    }

    @Override
    public BusRespond toRespond(Bus bus) {
        if ( bus == null ) {
            return null;
        }

        BusRespond busRespond = new BusRespond();

        busRespond.setBus_type( bus.getBus_type() );
        busRespond.setId( bus.getId() );
        busRespond.setLicense_plate( bus.getLicense_plate() );

        return busRespond;
    }
}
