package com.bookingticket.dto.respond;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.User;
import com.bookingticket.enumtype.TicketStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
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
    Bus bus;
    User user;
    String seat_number;
    @Setter
    @Getter
    LocalDateTime departure_time;
    BigDecimal price;
    String status;

}
