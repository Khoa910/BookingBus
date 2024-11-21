//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.BusCompanyRequest;
//import com.bookingticket.dto.respond.BusCompanyRespond;
//import com.bookingticket.entity.BusCompany;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface BusCompanyMapper {
//
//    BusCompanyMapper INSTANCE = Mappers.getMapper(BusCompanyMapper.class);
//
//    // Ánh xạ từ BusCompanyRequest sang BusCompany entity
//    BusCompany toEntity(BusCompanyRequest busCompanyRequest);
//
//    // Ánh xạ từ BusCompany entity sang BusCompanyRespond DTO
//    BusCompanyRespond toRespond(BusCompany busCompany);
//}