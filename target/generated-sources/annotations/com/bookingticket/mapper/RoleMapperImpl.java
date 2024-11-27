package com.bookingticket.mapper;

import com.bookingticket.dto.request.RoleRequest;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-27T09:35:26+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
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
