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
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", referencedColumnName = "id", nullable = true)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    private String seat_number;

    private LocalDateTime departure_time;

    private BigDecimal price;

    private String status;

    private String randomDate; // Không cần ánh xạ vào cơ sở dữ liệu

    // Getter và Setter cho randomDate
    public String getRandomDate() {
        return randomDate;
    }

    public void setRandomDate(String randomDate) {
        this.randomDate = randomDate;
    }

}
