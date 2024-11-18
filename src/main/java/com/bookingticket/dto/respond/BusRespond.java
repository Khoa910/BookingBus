package com.bookingticket.dto.respond;

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
    Integer seat_count;
    String bus_type;
    BusCompanyRespond bus_company_id;
    BusStationRespond departureStation_id;
    BusStationRespond arrivalStation_id;
}

