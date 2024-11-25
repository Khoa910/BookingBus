package com.bookingticket.dto.respond;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusScheduleDisplayRespond {
    Long id;
    Long bus_id;
    Long departureStation_id;
    Long arrivalStation_id;
    String departureStationName;  // Tên của điểm đi
    String arrivalStationName;    // Tên của điểm đến
    LocalDateTime departure_time;
    LocalDateTime arrival_time;
    BigDecimal price;
}