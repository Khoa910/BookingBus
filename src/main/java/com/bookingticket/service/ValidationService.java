package com.bookingticket.service;

import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.User;
import com.bookingticket.repository.BusScheduleRepository;
import com.bookingticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final UserRepository userRepository;
    private final BusScheduleRepository busScheduleRepository;
    public ValidationService(UserRepository userRepository, BusScheduleRepository busScheduleRepository) {
        this.userRepository = userRepository;
        this.busScheduleRepository = busScheduleRepository;
    }

    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    BusSchedule validateSchedule(Long scheduleId) {
        return busScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Lịch trình không tồn tại"));
    }
}