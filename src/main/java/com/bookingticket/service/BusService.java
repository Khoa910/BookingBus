package com.bookingticket.service;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.SeatType;
import com.bookingticket.mapper.BusMapper;
import com.bookingticket.repository.BusCompanyRepository;
import com.bookingticket.repository.BusRepository;
import com.bookingticket.repository.BusStationRepository;
import com.bookingticket.repository.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final BusStationRepository busStationRepository;
    private final BusMapper busMapper;

    @Autowired
    public BusService(BusRepository busRepository, BusStationRepository busStationRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busStationRepository = busStationRepository;
        this.busMapper = busMapper;
    }

    public List<BusRespond> getAllBuses() {
        List<Bus> buses = busRepository.findAll();
        return buses.stream().map(busMapper::toRespond).toList();
    }


    public Optional<BusRespond> getBusById(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(busMapper::toRespond);
    }

    public BusRespond addBus(BusRequest busRequest) {

        Optional<Bus> existingBus = busRepository.findByLicensePlate(busRequest.getLicense_plate());
        if (existingBus.isPresent()) {

            throw new IllegalArgumentException("License plate already exists, please use another license plate!!!");
        }

        Bus bus = busMapper.toEntity(busRequest);
        Bus savedBus = busRepository.save(bus);
        return busMapper.toRespond(savedBus);
    }

    public Optional<BusRespond> updateBus(Long id, Long departureStationId, Long arrivalStationId) {
        Optional<Bus> existingBus = busRepository.findById(id);
        if (existingBus.isPresent()) {
            Bus bus = existingBus.get();

            Optional<BusStation> departureStation = busStationRepository.findById(departureStationId);
            Optional<BusStation> arrivalStation = busStationRepository.findById(arrivalStationId);

            if (departureStation.isPresent()) {
                bus.setDepartureStation(departureStation.get());
            } else {
                throw new IllegalArgumentException("DepartureStation does not exist !!!");
            }

            if (arrivalStation.isPresent()) {
                bus.setArrivalStation(arrivalStation.get());
            } else {
                throw new IllegalArgumentException("ArrivalStation does not exist !!!");
            }

            Bus updatedBus = busRepository.save(bus);

            return Optional.of(busMapper.toRespond(updatedBus));
        }
        return Optional.empty();
    }

    public boolean deleteBus(Long busId) {
        if (busRepository.existsById(busId)) {
            busRepository.deleteById(busId);
            return true;
        }
        return false;
    }

}
