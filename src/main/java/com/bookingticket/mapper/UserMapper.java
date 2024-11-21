//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.UserRequest;
//import com.bookingticket.dto.respond.UserRespond;
//import com.bookingticket.entity.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface UserMapper {
//
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    // Ánh xạ từ UserRequest sang User entity
//    User toEntity(UserRequest userRequest);
//
//    // Ánh xạ từ User entity sang UserRespond DTO
//    UserRespond toRespond(User user);
//}
