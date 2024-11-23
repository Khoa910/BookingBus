package com.bookingticket.mapper;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {


    @Mapping(target = "bus.id", source = "bus_id")
    @Mapping(target = "user.id", source = "user_id")
    @Mapping(target = "seat_number", source = "seat_number")
    @Mapping(target = "departure_time", source = "departure_time")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "status", source = "status")
    Ticket toEntity(TicketRequest ticketRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "bus_id", source = "bus.id")
    @Mapping(target = "user_id", source = "user.id")
    @Mapping(target = "seat_number", source = "seat_number")
    @Mapping(target = "departure_time", source = "departure_time")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "status", source = "status")
    TicketRespond toRespond(Ticket ticket);
}
