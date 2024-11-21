package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BusCompanyMapperImpl implements BusCompanyMapper {

    @Override
    public BusCompany toEntity(BusCompanyRequest busCompanyRequest) {
        if ( busCompanyRequest == null ) {
            return null;
        }

        BusCompany busCompany = new BusCompany();

        return busCompany;
    }

    @Override
    public BusCompanyRespond toRespond(BusCompany busCompany) {
        if ( busCompany == null ) {
            return null;
        }

        BusCompanyRespond busCompanyRespond = new BusCompanyRespond();

        return busCompanyRespond;
    }
}
