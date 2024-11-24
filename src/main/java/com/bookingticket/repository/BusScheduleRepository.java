package com.bookingticket.repository;

import com.bookingticket.entity.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {
    List<BusSchedule> findByDepartureStationId(Long departureStationId);
    @Query("SELECT bs FROM BusSchedule bs " +
            "WHERE bs.departureStation.id = :departureStationId " +
            "AND bs.arrivalStation.id = :arrivalStationId " +
            "AND bs.departure_time > :departureTime")
    List<BusSchedule> findByDepartureStation_IdAndArrivalStation_IdAndDepartureTimeAfter(
            @Param("departureStationId") Long departureStationId,
            @Param("arrivalStationId") Long arrivalStationId,
            @Param("departureTime") LocalDateTime departureTime
    );
}
