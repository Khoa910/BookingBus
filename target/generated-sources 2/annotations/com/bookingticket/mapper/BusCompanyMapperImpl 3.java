package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-22T16:09:13+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class BusCompanyMapperImpl implements BusCompanyMapper {

    @Override
    public BusCompany toEntity(BusCompanyRequest busCompanyRequest) {
        if ( busCompanyRequest == null ) {
            return null;
        }

        BusCompany busCompany = new BusCompany();

        busCompany.setName( busCompanyRequest.getName() );
        busCompany.setPhone_number( busCompanyRequest.getPhone_number() );

        return busCompany;
    }

    @Override
    public BusCompanyRespond toRespond(BusCompany busCompany) {
        if ( busCompany == null ) {
            return null;
        }

        BusCompanyRespond busCompanyRespond = new BusCompanyRespond();

        busCompanyRespond.setName( busCompany.getName() );
        busCompanyRespond.setPhone_number( busCompany.getPhone_number() );

        return busCompanyRespond;
    }
}
