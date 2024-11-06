package com.bookingticket.dto.request;
import com.bookingticket.enumtype.SeatStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatRequest {
    String id_seat;
    Long bus_id;
    SeatStatus status;
    String seat_name;
}
