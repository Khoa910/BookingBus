package com.bookingticket.service;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusStation;
import com.bookingticket.mapper.BusScheduleMapper;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.repository.BusStationRepository;
import com.bookingticket.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusScheduleService {

    private final BusScheduleRepository busScheduleRepository;
    private final BusRepository busRepository;
    private final BusStationRepository busStationRepository;
    private final BusScheduleMapper busScheduleMapper;

    @Autowired
    public BusScheduleService(BusScheduleRepository busScheduleRepository, BusRepository busRepository, BusStationRepository busStationRepository, BusScheduleMapper busScheduleMapper) {
        this.busScheduleRepository = busScheduleRepository;
        this.busRepository = busRepository;
        this.busStationRepository = busStationRepository;
        this.busScheduleMapper = busScheduleMapper;
    }

    public List<BusScheduleRespond> getAllBusSchedules() {
        List<BusSchedule> busSchedules = busScheduleRepository.findAll();
        return busSchedules.stream()
                .map(busScheduleMapper::toRespond)
                .toList();
    }

    public Optional<BusScheduleRespond> getBusScheduleById(Long id) {
        Optional<BusSchedule> busSchedule = busScheduleRepository.findById(id);
        return busSchedule.map(busScheduleMapper::toRespond);
    }


    public BusScheduleRespond addBusSchedule(BusScheduleRequest busScheduleRequest) {

        Optional<Bus> bus = busRepository.findById(busScheduleRequest.getBus_id());
        Optional<BusStation> departureStation = busStationRepository.findById(busScheduleRequest.getDepartureStation_id());
        Optional<BusStation> arrivalStation = busStationRepository.findById(busScheduleRequest.getArrivalStation_id());

        if (bus.isEmpty()) {
            throw new IllegalArgumentException("Bus not found with id: " + busScheduleRequest.getBus_id());
        }
        if (departureStation.isEmpty()) {
            throw new IllegalArgumentException("Departure station not found with id: " + busScheduleRequest.getDepartureStation_id());
        }
        if (arrivalStation.isEmpty()) {
            throw new IllegalArgumentException("Arrival station not found with id: " + busScheduleRequest.getArrivalStation_id());
        }

        BusSchedule busSchedule = busScheduleMapper.toEntity(busScheduleRequest);
        busSchedule.setBus(bus.get());
        busSchedule.setDepartureStation(departureStation.get());
        busSchedule.setArrivalStation(arrivalStation.get());

        BusSchedule savedBusSchedule = busScheduleRepository.save(busSchedule);
        return busScheduleMapper.toRespond(savedBusSchedule);
    }

    public Optional<BusScheduleRespond> updateBusSchedule(Long id, BusScheduleRequest busScheduleRequest) {
        Optional<BusSchedule> existingBusSchedule = busScheduleRepository.findById(id);

        if (existingBusSchedule.isEmpty()) {
            throw new IllegalArgumentException("BusSchedule not found with id: " + id);
        }

        Optional<Bus> bus = busRepository.findById(busScheduleRequest.getBus_id());
        Optional<BusStation> departureStation = busStationRepository.findById(busScheduleRequest.getDepartureStation_id());
        Optional<BusStation> arrivalStation = busStationRepository.findById(busScheduleRequest.getArrivalStation_id());

        if (bus.isEmpty()) {
            throw new IllegalArgumentException("Bus not found with id: " + busScheduleRequest.getBus_id());
        }
        if (departureStation.isEmpty()) {
            throw new IllegalArgumentException("Departure station not found with id: " + busScheduleRequest.getDepartureStation_id());
        }
        if (arrivalStation.isEmpty()) {
            throw new IllegalArgumentException("Arrival station not found with id: " + busScheduleRequest.getArrivalStation_id());
        }

        BusSchedule busSchedule = existingBusSchedule.get();
        busSchedule.setBus(bus.get());
        busSchedule.setDepartureStation(departureStation.get());
        busSchedule.setArrivalStation(arrivalStation.get());
        busSchedule.setDeparture_time(busScheduleRequest.getDeparture_time());
        busSchedule.setArrival_time(busScheduleRequest.getArrival_time());
        busSchedule.setPrice(busScheduleRequest.getPrice());

        BusSchedule updatedBusSchedule = busScheduleRepository.save(busSchedule);
        return Optional.of(busScheduleMapper.toRespond(updatedBusSchedule));
    }

    public boolean deleteBusSchedule(Long id) {
        if (busScheduleRepository.existsById(id)) {
            busScheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
