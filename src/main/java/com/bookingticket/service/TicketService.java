package com.bookingticket.service;

import com.bookingticket.dto.request.TicketRequest;
import com.bookingticket.dto.respond.TicketRespond;
import com.bookingticket.entity.Ticket;
import com.bookingticket.enumtype.TicketStatus;
import com.bookingticket.mapper.TicketMapper;
import com.bookingticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<TicketRespond> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toRespond)
                .collect(Collectors.toList());
    }

    public TicketRespond getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.map(ticketMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    public TicketRespond createTicket(TicketRequest ticketRequest) {
        Ticket ticket = ticketMapper.toEntity(ticketRequest);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toRespond(savedTicket);
    }

    public TicketRespond updateTicket(Long id, TicketRequest ticketRequest) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setSeat_number(ticketRequest.getSeat_number());
            ticket.setDeparture_time(ticketRequest.getDeparture_time());
            ticket.setPrice(ticketRequest.getPrice());

            String statusString = String.valueOf(ticketRequest.getStatus());
            if (statusString != null && !statusString.isEmpty()) {
                try {
                    ticket.setStatus(String.valueOf(TicketStatus.valueOf(statusString)));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid status value: " + statusString);
                }
            } else {
                throw new RuntimeException("Status is required.");
            }

            Ticket updatedTicket = ticketRepository.save(ticket);
            return ticketMapper.toRespond(updatedTicket);
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    public void deleteTicket(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            ticketRepository.delete(ticketOptional.get());
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }
}
