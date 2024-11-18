package com.bookingticket.dto.request;
import com.bookingticket.enumtype.TicketStatus;
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
public class TicketRequest {
    Long bus_id;
    Long user_id;
    String seat_number;
    LocalDateTime departure_time;
    BigDecimal price;
    TicketStatus status;
}
