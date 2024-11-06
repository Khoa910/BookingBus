package com.bookingticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "busschedule")
public class BusSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "departure_station_id", referencedColumnName = "id")
    private BusStation departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id", referencedColumnName = "id")
    private BusStation arrivalStation;

    private LocalDateTime departure_time;

    private LocalDateTime arrival_time;

    private BigDecimal price;


}
