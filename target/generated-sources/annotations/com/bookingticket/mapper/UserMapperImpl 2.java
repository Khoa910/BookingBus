package com.bookingticket.mapper;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.request.UserRequestOAuth;
import com.bookingticket.dto.respond.RoleRespond;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T00:19:38+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setRole( userRequestToRole( userRequest ) );
        user.setAddress( userRequest.getAddress() );
        user.setEmail( userRequest.getEmail() );
        user.setFull_name( userRequest.getFull_name() );
        user.setPassword( userRequest.getPassword() );
        user.setPhone_number( userRequest.getPhone_number() );
        user.setUsername( userRequest.getUsername() );

        return user;
    }

    @Override
    public UserRespond toRespond(User user) {
        if ( user == null ) {
            return null;
        }

        UserRespond userRespond = new UserRespond();

        userRespond.setRole( roleToRoleRespond( user.getRole() ) );
        userRespond.setAddress( user.getAddress() );
        userRespond.setEmail( user.getEmail() );
        userRespond.setFull_name( user.getFull_name() );
        userRespond.setId( user.getId() );
        userRespond.setPhone_number( user.getPhone_number() );
        userRespond.setUsername( user.getUsername() );

        return userRespond;
    }

    @Override
    public User toEntity(UserRequestOAuth userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setRole( userRequestOAuthToRole( userRequest ) );
        user.setAddress( userRequest.getAddress() );
        user.setEmail( userRequest.getEmail() );
        user.setFull_name( userRequest.getFull_name() );
        user.setPassword( userRequest.getPassword() );
        user.setPhone_number( userRequest.getPhone_number() );
        user.setUsername( userRequest.getUsername() );

        return user;
    }

    protected Role userRequestToRole(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( userRequest.getRole() );

        return role;
    }

    protected RoleRespond roleToRoleRespond(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleRespond roleRespond = new RoleRespond();

        roleRespond.setId( role.getId() );
        roleRespond.setName( role.getName() );

        return roleRespond;
    }

    protected Role userRequestOAuthToRole(UserRequestOAuth userRequestOAuth) {
        if ( userRequestOAuth == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( userRequestOAuth.getRole() );

        return role;
    }
}
