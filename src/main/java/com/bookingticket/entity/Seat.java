package com.bookingticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    private String id_seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", referencedColumnName = "id", nullable = true)
    private Bus bus;

    private String status;

    private String seat_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id", referencedColumnName = "id", nullable = true)
    private SeatType seatType;
}
