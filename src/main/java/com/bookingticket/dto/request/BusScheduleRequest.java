package com.bookingticket.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusScheduleRequest {
    Long bus_id;
    Long departureStation_id;
    Long arrivalStation_id;
    LocalDateTime departure_time;
    LocalDateTime arrival_time;
    BigDecimal price;
}
