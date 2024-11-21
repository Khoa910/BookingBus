package com.bookingticket.mapper;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.User;
import com.bookingticket.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "role.id", source = "role")
    User toEntity(UserRequest userRequest);

   
    @Mapping(target = "role", source = "role")
    UserRespond toRespond(User user);
}
