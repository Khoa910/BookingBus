package com.bookingticket.mapper;

import com.bookingticket.entity.User;
import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping from UserRequest to User entity
//    @Mapping(source = "role", target = "role.id")
    User userRequestToUser(UserRequest userRequest);

    // Mapping from User entity to UserRespond DTO
//    @Mapping(source = "role", target = "role")
    UserRespond userToUserRespond(User user);
}
