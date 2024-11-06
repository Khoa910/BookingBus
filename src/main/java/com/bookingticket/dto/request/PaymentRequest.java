package com.bookingticket.dto.request;
import com.bookingticket.enumtype.PaymentMethod;
import com.bookingticket.enumtype.StatusPayment;
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
public class PaymentRequest {
    Long ticket_id;
    PaymentMethod payment_method;
    BigDecimal amount;
//    LocalDateTime payment_time;
    StatusPayment status;

}
