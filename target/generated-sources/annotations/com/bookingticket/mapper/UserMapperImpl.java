package com.bookingticket.mapper;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userRequestToUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        return user;
    }

    @Override
    public UserRespond userToUserRespond(User user) {
        if ( user == null ) {
            return null;
        }

        UserRespond userRespond = new UserRespond();

        return userRespond;
    }
}
