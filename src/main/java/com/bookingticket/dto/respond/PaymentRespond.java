package com.bookingticket.dto.respond;

import com.bookingticket.enumtype.PaymentMethod;
import com.bookingticket.enumtype.StatusPayment;
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
public class PaymentRespond {
    Long id;
    TicketRespond ticket_id;
    String payment_method;
    BigDecimal amount;
    LocalDateTime payment_time;
    String status;
}
