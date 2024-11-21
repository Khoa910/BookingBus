package com.bookingticket.mapper;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Ticket;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:25:06+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toEntity(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setDeparture_time( ticketRequest.getDeparture_time() );
        ticket.setPrice( ticketRequest.getPrice() );
        ticket.setSeat_number( ticketRequest.getSeat_number() );
        if ( ticketRequest.getStatus() != null ) {
            ticket.setStatus( ticketRequest.getStatus().name() );
        }

        return ticket;
    }

    @Override
    public TicketRespond toRespond(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        TicketRespond ticketRespond = new TicketRespond();

        ticketRespond.setDeparture_time( ticket.getDeparture_time() );
        ticketRespond.setId( ticket.getId() );
        ticketRespond.setPrice( ticket.getPrice() );
        ticketRespond.setSeat_number( ticket.getSeat_number() );
        ticketRespond.setStatus( ticket.getStatus() );

        return ticketRespond;
    }
}
