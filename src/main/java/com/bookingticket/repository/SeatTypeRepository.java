package com.bookingticket.repository;

import com.bookingticket.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
}
