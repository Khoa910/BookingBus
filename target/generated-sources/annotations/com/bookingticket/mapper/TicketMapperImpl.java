package com.bookingticket.mapper;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.Ticket;
import com.bookingticket.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T21:55:37+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toEntity(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setBus( ticketRequestToBus( ticketRequest ) );
        ticket.setUser( ticketRequestToUser( ticketRequest ) );
        ticket.setSeat_number( ticketRequest.getSeat_number() );
        ticket.setDeparture_time( ticketRequest.getDeparture_time() );
        ticket.setPrice( ticketRequest.getPrice() );
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

        ticketRespond.setId( ticket.getId() );
        ticketRespond.setBus_id( ticketBusId( ticket ) );
        ticketRespond.setUser_id( ticketUserId( ticket ) );
        ticketRespond.setSeat_number( ticket.getSeat_number() );
        ticketRespond.setDeparture_time( ticket.getDeparture_time() );
        ticketRespond.setPrice( ticket.getPrice() );
        ticketRespond.setStatus( ticket.getStatus() );

        return ticketRespond;
    }

    protected Bus ticketRequestToBus(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Bus bus = new Bus();

        bus.setId( ticketRequest.getBus_id() );

        return bus;
    }

    protected User ticketRequestToUser(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        User user = new User();

        user.setId( ticketRequest.getUser_id() );

        return user;
    }

    private Long ticketBusId(Ticket ticket) {
        Bus bus = ticket.getBus();
        if ( bus == null ) {
            return null;
        }
        return bus.getId();
    }

    private Long ticketUserId(Ticket ticket) {
        User user = ticket.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
