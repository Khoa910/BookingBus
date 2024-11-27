package com.bookingticket.dto.respond;

import com.bookingticket.entity.BusStation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class BusScheduleRespond {
    Long id;
    Long bus_id;
    Long departureStation_id;
    Long arrivalStation_id;
    BusStation departureStation;
    BusStation arrivalStation;
    LocalDateTime departure_time;
    LocalDateTime arrival_time;
    BigDecimal price;
}
