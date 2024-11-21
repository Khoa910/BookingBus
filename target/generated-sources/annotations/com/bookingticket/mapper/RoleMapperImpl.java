package com.bookingticket.mapper;

import com.bookingticket.dto.request.RoleRequest;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.entity.Role;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:26:41+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleRequest.getId() );
        role.setName( roleRequest.getName() );

        return role;
    }

    @Override
    public RoleRespond toRespond(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleRespond roleRespond = new RoleRespond();

        roleRespond.setId( role.getId() );
        roleRespond.setName( role.getName() );

        return roleRespond;
    }
}
