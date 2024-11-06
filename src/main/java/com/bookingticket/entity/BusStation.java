package com.bookingticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        @OneToMany(mappedBy = "departureStation",cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<BusSchedule> departures = new HashSet<>();

        @OneToMany(mappedBy = "arrivalStation", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<BusSchedule> arrivals = new HashSet<>();
}
