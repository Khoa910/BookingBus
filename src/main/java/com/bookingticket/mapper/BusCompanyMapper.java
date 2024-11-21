package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusCompanyMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone_number", target = "phone_number")
    BusCompany toEntity(BusCompanyRequest busCompanyRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone_number", target = "phone_number")
    BusCompanyRespond toRespond(BusCompany busCompany);
}
