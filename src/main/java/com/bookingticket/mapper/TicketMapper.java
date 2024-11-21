package com.bookingticket.mapper;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    // Ánh xạ từ TicketRequest sang Ticket entity
    Ticket toEntity(TicketRequest ticketRequest);

    // Ánh xạ từ Ticket entity sang TicketRespond
    TicketRespond toRespond(Ticket ticket);
}
