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

    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "id")
    private Bus bus;

    private String status;

    private String seat_name;

}
