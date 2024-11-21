package com.bookingticket.mapper;

import com.bookingticket.dto.request.RoleRequest;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    // Ánh xạ từ RoleRequest sang Role entity
    Role toEntity(RoleRequest roleRequest);

    // Ánh xạ từ Role entity sang RoleRespond
    RoleRespond toRespond(Role role);
}
