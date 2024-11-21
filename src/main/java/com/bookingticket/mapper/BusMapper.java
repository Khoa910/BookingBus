//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.BusRequest;
//import com.bookingticket.dto.respond.BusRespond;
//import com.bookingticket.entity.Bus;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface BusMapper {
//
//    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);
//
//    // Ánh xạ từ DTO sang Entity
//    @Mapping(target = "bus_company.id", source = "bus_company_id")
//    @Mapping(target = "departureStation.id", source = "departureStation_id")
//    @Mapping(target = "arrivalStation.id", source = "arrivalStation_id")
//    @Mapping(target = "bus_type", source = "bus_type")
//    Bus toEntity(BusRequest busRequest);
//
//    // Ánh xạ từ Entity sang DTO
//    @Mapping(target = "bus_company_id", source = "bus_company")
//    @Mapping(target = "departureStation_id", source = "departureStation")
//    @Mapping(target = "arrivalStation_id", source = "arrivalStation")
//    @Mapping(target = "bus_type", source = "bus_type")
//    BusRespond toRespond(Bus bus);
//}
