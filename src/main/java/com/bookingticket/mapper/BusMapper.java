package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusMapper {

    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    // Ánh xạ từ BusRequest sang Bus entity
//    @Mapping(target = "busCompany.id", source = "bus_company_id")  // Thay đổi tên từ "bus_company.id"
//    @Mapping(target = "seatType.id", source = "seat_type_id")
//    @Mapping(target = "departureStation.id", source = "departureStation_id")
//    @Mapping(target = "arrivalStation.id", source = "arrivalStation_id")
//    @Mapping(target = "busType", source = "bus_type")  // Thay đổi tên từ "bus_type"
    Bus toEntity(BusRequest busRequest);

    // Ánh xạ từ Bus entity sang BusRespond
//    @Mapping(target = "bus_company_id", source = "busCompany.id")  // Thay đổi tên từ "busCompany.id"
//    @Mapping(target = "seat_type_id", source = "seatType.id")
//    @Mapping(target = "departureStation_id", source = "departureStation.id")
//    @Mapping(target = "arrivalStation_id", source = "arrivalStation.id")
//    @Mapping(target = "bus_type", source = "busType")  // Thay đổi tên từ "busType"
    BusRespond toRespond(Bus bus);
}
