package com.bookingticket.dto.respond;

import com.bookingticket.enumtype.TicketStatus;
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
public class TicketRespond {
    Long id;
    Long bus_id;
    Long user_id;
    String seat_number;
    LocalDateTime departure_time;
    BigDecimal price;
    String status;
}
