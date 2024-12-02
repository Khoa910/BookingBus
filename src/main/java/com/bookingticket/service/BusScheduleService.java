package com.bookingticket.service;

import com.bookingticket.dto.request.BusScheduleRequest;
import com.bookingticket.dto.respond.BusScheduleDisplayRespond;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.dto.respond.ScheduleInfoRespond;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusStation;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.BusScheduleMapper;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.repository.BusStationRepository;
import com.bookingticket.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusScheduleService {

    private final BusScheduleRepository busScheduleRepository;
    private final BusRepository busRepository;
    private final BusStationRepository busStationRepository;
    private final BusScheduleMapper busScheduleMapper;
    private final BusStationService busStationService;

    @Autowired
    public BusScheduleService(BusScheduleRepository busScheduleRepository, BusRepository busRepository, BusStationRepository busStationRepository, BusScheduleMapper busScheduleMapper, BusStationService busStationService) {
        this.busScheduleRepository = busScheduleRepository;
        this.busRepository = busRepository;
        this.busStationRepository = busStationRepository;
        this.busScheduleMapper = busScheduleMapper;
        this.busStationService = busStationService;
    }

    public List<BusScheduleRespond> getAllBusSchedules() {
        List<BusSchedule> busSchedules = busScheduleRepository.findAll();
        return busSchedules.stream()
                .map(busScheduleMapper::toRespond)
                .toList();
    }
    public List<BusScheduleRespond> getBusSchedulesByDepartureStationId(Long departureStationId) {
        List<BusSchedule> busSchedules = busScheduleRepository.findByDepartureStationId(departureStationId);
        return busSchedules.stream()
                .map(busScheduleMapper::toRespond)
                .collect(Collectors.toList());
    }

    public List<BusScheduleDisplayRespond> getAllDisplaySchedules() {
        List<BusSchedule> busSchedules = busScheduleRepository.findAll();
        return busSchedules.stream()
                .map(busScheduleMapper::toDisplayRespond)
                .collect(Collectors.toList());
    }

        public List<BusSchedule> getAllDisplaySchedules2() {
            return busScheduleRepository.findAll();
        }

    public boolean addSchedule(BusSchedule schedule) {
        try {
            busScheduleRepository.save(schedule); // Lưu chuyến xe mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public List<BusScheduleDisplayRespond> getDisplaySchedulesByDepartureStationId(Long departureStationId) {
        List<BusSchedule> busSchedules = busScheduleRepository.findByDepartureStationId(departureStationId);
        return busSchedules.stream()
                .map(busScheduleMapper::toDisplayRespond)
                .collect(Collectors.toList());
    }

    public Optional<BusSchedule> getBusScheduleById(String BusScheduleId) {
        return busScheduleRepository.findById(Long.parseLong(BusScheduleId));
    }

    public Optional<BusScheduleRespond> getBusScheduleById(Long id) {
        Optional<BusSchedule> busSchedule = busScheduleRepository.findById(id);
        return busSchedule.map(busScheduleMapper::toRespond);
    }

    public BusSchedule findById(Long id) {
        return busScheduleRepository.findByIdQuery(id);
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

    public List<ScheduleInfoRespond> getAllSchedulesInfo() {
        List<BusSchedule> busSchedules = busScheduleRepository.findAll();

        return busSchedules.stream()
                .map(schedule -> new ScheduleInfoRespond(
                        schedule.getId(),
                        schedule.getBus(),
                        schedule.getDepartureStation().getName(),
                        schedule.getArrivalStation().getName(),
                        schedule.getDeparture_time(),
                        schedule.getArrival_time(),
                        schedule.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public List<BusSchedule> findMatchingSchedules(String departureStationName, String arrivalStationName, LocalDateTime departureTime) {
        // Tìm ID của điểm đi
        Long departureStationId = busStationRepository.findByName(departureStationName).get(0).getId();
        // Tìm ID của điểm đến
        Long arrivalStationId = busStationRepository.findByName(arrivalStationName).get(0).getId();

        // Tìm lịch trình phù hợp
        return busScheduleRepository.findByDepartureStation_IdAndArrivalStation_IdAndDepartureTimeAfter(
                departureStationId, arrivalStationId, departureTime
        );
    }

    public List<BusSchedule> getSchedulesWithOptionalParams(Long departureStationId, Long arrivalStationId, LocalDateTime departureTime) {
        return busScheduleRepository.findSchedulesWithOptionalParams(departureStationId, arrivalStationId, departureTime);
    }

    public List<BusSchedule> getSchedulesWithNames(String departureStationName, String arrivalStationName, LocalDateTime departureTime) {
        return busScheduleRepository.findSchedulesWithNames(departureStationName, arrivalStationName, departureTime);
    }
    public BusSchedule getScheduleById(Long id) {
        return busScheduleRepository.findById(id).get();
    }

}
