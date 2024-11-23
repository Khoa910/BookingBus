package com.bookingticket.repository;

import com.bookingticket.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("SELECT b FROM Bus  b WHERE b.license_plate = :license_plate")
    Optional<Bus> findByLicensePlate(@Param("license_plate") String licensePlate);
}
