package com.bookingticket.repository;

import com.bookingticket.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {
}
