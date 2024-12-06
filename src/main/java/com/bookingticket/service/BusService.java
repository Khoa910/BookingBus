package com.bookingticket.service;

import com.bookingticket.dto.request.BusRequest;
import com.bookingticket.dto.respond.BusRespond;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusStation;
import com.bookingticket.mapper.BusMapper;
import com.bookingticket.repository.BusRepository;
import com.bookingticket.repository.BusStationRepository;
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

    public List<Bus> getAllBuses2(){
        return busRepository.findAll();
    }

    public Optional<BusRespond> getBusById(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(busMapper::toRespond);
    }

    public Optional<Bus> getBusById2(Long id) {
        return busRepository.findById(id);
    }

    public Bus getBusById3(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    public Bus findByLicensePlate(String licensePlate) {
        return busRepository.findByLicensePlate(licensePlate)
                .orElse(null); // Use Optional to handle null values
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

    public boolean addBuss(Bus bus) {
        try {
            busRepository.save(bus); // Lưu xe mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
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

    public boolean updateBus2(Bus bus) {
        Bus Buss = busRepository.findById(bus.getId()).orElse(null);
        if (Buss != null) {
            Buss.setLicense_plate(bus.getLicense_plate());
            Buss.getSeatType().setId(bus.getSeatType().getId());
            Buss.setBus_type(bus.getBus_type());
            Buss.getBus_company().setId(bus.getBus_company().getId());
            busRepository.save(Buss);
            return true;
        }
        return false;
    }

    public void deleteBus(Long busId) {
        Optional<Bus> BusOptional = busRepository.findById(busId);
        if (BusOptional.isPresent()) {
            busRepository.delete(BusOptional.get());
        } else {
            throw new RuntimeException("BusStation not found with id: " + busId);
        }
    }

}
