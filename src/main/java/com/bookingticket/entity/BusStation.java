package com.bookingticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "busstation")
public class BusStation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String address;

        @OneToMany(mappedBy = "departureStation")
        private List<BusSchedule> departures;

        @OneToMany(mappedBy = "arrivalStation")
        private List<BusSchedule> arrivals;
}
