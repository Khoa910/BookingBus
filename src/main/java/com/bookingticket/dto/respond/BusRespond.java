package com.bookingticket.dto.respond;

import com.bookingticket.entity.SeatType;
import com.bookingticket.enumtype.BusType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusRespond {
    Long id;
    String license_plate;
    Long seat_type_id;
    String bus_type;
    Long bus_company_id;
    Long departureStation_id;
    Long arrivalStation_id;
}

