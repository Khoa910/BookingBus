package com.bookingticket.mapper;


import com.bookingticket.dto.request.RoleRequest;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Role toEntity(RoleRequest roleRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleRespond toRespond(Role role);
}
