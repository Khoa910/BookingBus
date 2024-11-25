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
    @Query("SELECT b FROM BusSchedule b " +
            "WHERE (:departureStationId IS NULL OR b.departureStation.id = :departureStationId) " +
            "AND (:arrivalStationId IS NULL OR b.arrivalStation.id = :arrivalStationId) " +
            "AND (:departureTime IS NULL OR b.departure_time >= :departureTime)")
    List<BusSchedule> findSchedulesWithOptionalParams(Long departureStationId, Long arrivalStationId, LocalDateTime departureTime);

    @Query("SELECT b FROM BusSchedule b " +
            "WHERE (:departureStationName IS NULL OR b.departureStation.name = :departureStationName) " +
            "AND (:arrivalStationName IS NULL OR b.arrivalStation.name = :arrivalStationName) " +
            "AND (:departureTime IS NULL OR b.departure_time >= :departureTime)")
    List<BusSchedule> findSchedulesWithNames(String departureStationName, String arrivalStationName, LocalDateTime departureTime);
}
