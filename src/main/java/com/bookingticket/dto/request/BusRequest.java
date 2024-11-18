package com.bookingticket.dto.request;

import com.bookingticket.enumtype.BusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRequest {
    String license_plate;
    Integer seat_count;
    BusType bus_type;
    Long bus_company_id;
    Long departureStation_id;
    Long arrivalStation_id;
}
