package com.bookingticket.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "buscompany")
public class BusCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone_number;

    @OneToMany(mappedBy = "bus_company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bus> buses = new HashSet<>();
}
