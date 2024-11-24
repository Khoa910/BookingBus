package com.bookingticket.repository;

import com.bookingticket.entity.BusStation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {
    List<BusStation> findByName(String departureStationName);
}
