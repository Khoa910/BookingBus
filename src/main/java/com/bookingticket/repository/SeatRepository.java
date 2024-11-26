package com.bookingticket.repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.Seat;
import com.bookingticket.enumtype.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
     // Lấy danh sách ghế tầng trên (B)
    @Query("SELECT s FROM Seat s WHERE s.seat_name LIKE 'B%' AND s.bus.id = :busId")
    List<Seat> findUpstairsSeats(@Param("busId") Long busId);

    // Lấy danh sách ghế tầng dưới (A)
    @Query("SELECT s FROM Seat s WHERE s.seat_name LIKE 'A%' AND s.bus.id = :busId")
    List<Seat> findDownstairsSeats(@Param("busId") Long busId);

    @Query("SELECT s FROM Seat s WHERE s.id_seat IN :seatIds")
    List<Seat> findAllByIdSeat(@Param("seatIds") List<Long> seatIds);

    @Query("SELECT s FROM Seat s WHERE s.status = :status AND s.bus = :bus ORDER BY s.id_seat ASC")
    List<Seat> findAllByStatusAndBus(@Param("status") String status, @Param("bus") Bus bus);
}
