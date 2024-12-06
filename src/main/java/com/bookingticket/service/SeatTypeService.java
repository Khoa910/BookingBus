package com.bookingticket.service;

import com.bookingticket.dto.request.SeatTypeRequest;
import com.bookingticket.dto.respond.SeatTypeRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.SeatType;
import com.bookingticket.mapper.SeatTypeMapper;
import com.bookingticket.repository.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;
    private final SeatTypeMapper seatTypeMapper;

    @Autowired
    public SeatTypeService(SeatTypeRepository seatTypeRepository, SeatTypeMapper seatTypeMapper) {
        this.seatTypeRepository = seatTypeRepository;
        this.seatTypeMapper = seatTypeMapper;
    }

    public List<SeatTypeRespond> getAllSeatTypes() {
        List<SeatType> seatTypes = seatTypeRepository.findAll();
        return seatTypes.stream()
                .map(seatTypeMapper::toRespond)
                .collect(Collectors.toList());
    }

    public List<SeatType> getAllSeatTypes2(){
        return seatTypeRepository.findAll();
    }

    public SeatTypeRespond getSeatTypeById(Long id) {
        Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(id);
        return seatTypeOptional.map(seatTypeMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("SeatType not found with id: " + id));
    }

    public Optional<SeatType> getSeatTypeById2(long Id) {
        return seatTypeRepository.findById(Id);
    }

    public SeatType getSeatTypeById3(long Id) {
        return seatTypeRepository.findById(Id).orElse(null);
    }

    public SeatTypeRespond createSeatType(SeatTypeRequest seatTypeRequest) {
        SeatType seatType = seatTypeMapper.toEntity(seatTypeRequest);
        SeatType savedSeatType = seatTypeRepository.save(seatType);
        return seatTypeMapper.toRespond(savedSeatType);
    }


    public SeatTypeRespond updateSeatType(Long id, SeatTypeRequest seatTypeRequest) {
        Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(id);
        if (seatTypeOptional.isPresent()) {
            SeatType seatType = seatTypeOptional.get();
            seatType.setSeat_count(seatTypeRequest.getSeat_count());
            seatType.setDescription(seatTypeRequest.getDescription());
            SeatType updatedSeatType = seatTypeRepository.save(seatType);
            return seatTypeMapper.toRespond(updatedSeatType);
        } else {
            throw new RuntimeException("SeatType not found with id: " + id);
        }
    }

    public void deleteSeatType(Long id) {
        Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(id);
        if (seatTypeOptional.isPresent()) {
            seatTypeRepository.delete(seatTypeOptional.get());
        } else {
            throw new RuntimeException("SeatType not found with id: " + id);
        }
    }
}
