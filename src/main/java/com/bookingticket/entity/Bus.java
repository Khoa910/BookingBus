package com.bookingticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id", referencedColumnName = "id", nullable = true)
    private SeatType seatType;

    private String bus_type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_company_id", referencedColumnName = "id", nullable = true)
    private BusCompany bus_company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id", referencedColumnName = "id", nullable = true)
    private BusStation departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id", referencedColumnName = "id", nullable = true)
    private BusStation arrivalStation;


    
    

}
