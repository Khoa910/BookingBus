package com.bookingticket.service;

import com.bookingticket.dto.request.BusStationRequest;
import com.bookingticket.dto.respond.BusStationRespond;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.BusStationMapper;
import com.bookingticket.repository.BusStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusStationService {

    private final BusStationRepository busStationRepository;
    private final BusStationMapper busStationMapper;

    @Autowired
    public BusStationService(BusStationRepository busStationRepository, BusStationMapper busStationMapper) {
        this.busStationRepository = busStationRepository;
        this.busStationMapper = busStationMapper;
    }

    public List<BusStationRespond> getAllBusStations() {
        List<BusStation> busStations = busStationRepository.findAll();
        return busStations.stream()
                .map(busStationMapper::toRespond)
                .collect(Collectors.toList());
    }

    public List<BusStation> getAllBusStations2() {
        return busStationRepository.findAll();
    }

    public BusStationRespond getBusStationById(Long id) {
        Optional<BusStation> busStationOptional = busStationRepository.findById(id);
        return busStationOptional.map(busStationMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("BusStation not found with id: " + id));
    }

    public BusStationRespond createBusStation(BusStationRequest request) {
        BusStation busStation = busStationMapper.toEntity(request);
        BusStation savedBusStation = busStationRepository.save(busStation);
        return busStationMapper.toRespond(savedBusStation);
    }

    public boolean addStation(BusStation station) {
        try {
            busStationRepository.save(station); // Lưu tài khoản mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public BusStationRespond updateBusStation(Long id, BusStationRequest request) {
        Optional<BusStation> busStationOptional = busStationRepository.findById(id);
        if (busStationOptional.isPresent()) {
            BusStation busStation = busStationOptional.get();
            busStation.setName(request.getName());
            busStation.setAddress(request.getAddress());
            BusStation updatedBusStation = busStationRepository.save(busStation);
            return busStationMapper.toRespond(updatedBusStation);
        } else {
            throw new RuntimeException("BusStation not found with id: " + id);
        }
    }

    public void deleteBusStation(Long id) {
        Optional<BusStation> busStationOptional = busStationRepository.findById(id);
        if (busStationOptional.isPresent()) {
            busStationRepository.delete(busStationOptional.get());
        } else {
            throw new RuntimeException("BusStation not found with id: " + id);
        }
    }
}
