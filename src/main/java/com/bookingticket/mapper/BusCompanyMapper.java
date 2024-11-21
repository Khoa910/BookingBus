package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusCompanyMapper {

    BusCompanyMapper INSTANCE = Mappers.getMapper(BusCompanyMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "phone_number", target = "phone_number")
    })
    BusCompany toEntity(BusCompanyRequest busCompanyRequest);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "phone_number", target = "phone_number")
    })
    BusCompanyRespond toRespond(BusCompany busCompany);
}
