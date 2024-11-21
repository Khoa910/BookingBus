package com.bookingticket.mapper;

import com.bookingticket.dto.request.RoleRequest;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.entity.Role;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role role = new Role();

        return role;
    }

    @Override
    public RoleRespond toRespond(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleRespond roleRespond = new RoleRespond();

        return roleRespond;
    }
}
