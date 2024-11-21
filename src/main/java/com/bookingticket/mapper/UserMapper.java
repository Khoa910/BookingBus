// package com.bookingticket.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.factory.Mappers;

// import com.bookingticket.dto.request.UserRequest;
// import com.bookingticket.dto.respond.UserRespond;
// import com.bookingticket.entity.User;

// @Mapper(componentModel = "spring")
// public interface UserMapper {

//     UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//     // Mapping from UserRequest to User entity
// //    @Mapping(source = "role", target = "role.id")
//     User userToEntity(UserRequest userRequest);

//     // Mapping from User entity to UserRespond DTO
// //    @Mapping(source = "role", target = "role")
//     UserRespond userToUserRespond(User user);
// }
