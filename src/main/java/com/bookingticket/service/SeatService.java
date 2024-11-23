package com.bookingticket.service;

import com.bookingticket.dto.request.SeatRequest;
import com.bookingticket.dto.respond.SeatRespond;
import com.bookingticket.entity.Seat;
import com.bookingticket.entity.SeatType;
import com.bookingticket.mapper.SeatMapper;
import com.bookingticket.repository.SeatRepository;
import com.bookingticket.repository.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final SeatTypeRepository seatTypeRepository;
    @Autowired
    public SeatService(SeatRepository seatRepository, SeatMapper seatMapper, SeatTypeRepository seatTypeRepository) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.seatTypeRepository = seatTypeRepository;
    }

    public List<SeatRespond> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        return seats.stream()
                .map(seatMapper::toRespond)
                .collect(Collectors.toList());
    }

    public SeatRespond getSeatById(String id_seat) {
        Optional<Seat> seatOptional = seatRepository.findById(id_seat);
        return seatOptional.map(seatMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id_seat));
    }

    public SeatRespond createSeat(SeatRequest seatRequest) {
        Seat seat = seatMapper.toEntity(seatRequest);
        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toRespond(savedSeat);
    }

    public SeatRespond updateSeat(String id_seat, SeatRequest seatRequest) {
        Optional<Seat> seatOptional = seatRepository.findById(id_seat);
        if (seatOptional.isPresent()) {
            Seat seat = seatOptional.get();

            seat.setSeat_name(seatRequest.getSeat_name());
            seat.setStatus(seatRequest.getStatus().name());

            Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(seatRequest.getSeat_type_id());
            if (seatTypeOptional.isPresent()) {
                seat.setSeatType(seatTypeOptional.get());
            } else {
                throw new RuntimeException("SeatType not found with id: " + seatRequest.getSeat_type_id());
            }

            Seat updatedSeat = seatRepository.save(seat);
            return seatMapper.toRespond(updatedSeat);
        } else {
            throw new RuntimeException("Seat not found with id: " + id_seat);
        }
    }

    public void deleteSeat(String id_seat) {
        Optional<Seat> seatOptional = seatRepository.findById(id_seat);
        if (seatOptional.isPresent()) {
            seatRepository.delete(seatOptional.get());
        } else {
            throw new RuntimeException("Seat not found with id: " + id_seat);
        }
    }
}
