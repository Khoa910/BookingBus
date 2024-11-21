package com.bookingticket.mapper;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Ticket;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toEntity(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        return ticket;
    }

    @Override
    public TicketRespond toRespond(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        TicketRespond ticketRespond = new TicketRespond();

        return ticketRespond;
    }
}
