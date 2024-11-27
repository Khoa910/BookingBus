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
    date = "2024-11-27T19:56:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Homebrew)"
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
        user.setUsername( userRequest.getUsername() );
        user.setPassword( userRequest.getPassword() );
        user.setFull_name( userRequest.getFull_name() );
        user.setPhone_number( userRequest.getPhone_number() );
        user.setEmail( userRequest.getEmail() );
        user.setAddress( userRequest.getAddress() );

        return user;
    }

    @Override
    public UserRespond toRespond(User user) {
        if ( user == null ) {
            return null;
        }

        UserRespond userRespond = new UserRespond();

        userRespond.setRole( roleToRoleRespond( user.getRole() ) );
        userRespond.setId( user.getId() );
        userRespond.setUsername( user.getUsername() );
        userRespond.setFull_name( user.getFull_name() );
        userRespond.setPhone_number( user.getPhone_number() );
        userRespond.setEmail( user.getEmail() );
        userRespond.setAddress( user.getAddress() );

        return userRespond;
    }

    @Override
    public User toEntity(UserRequestOAuth userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setRole( userRequestOAuthToRole( userRequest ) );
        user.setUsername( userRequest.getUsername() );
        user.setPassword( userRequest.getPassword() );
        user.setFull_name( userRequest.getFull_name() );
        user.setPhone_number( userRequest.getPhone_number() );
        user.setEmail( userRequest.getEmail() );
        user.setAddress( userRequest.getAddress() );

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
