package com.bookingticket.dto.respond;
import com.bookingticket.entity.Bus;
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
public class ScheduleInfoRespond {
    private Long id;
    private Bus bus_id;
    private String departureStation; // Tên điểm đi
    private String arrivalStation;  // Tên điểm đến
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;

}
