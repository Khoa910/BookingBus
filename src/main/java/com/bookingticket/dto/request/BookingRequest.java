package com.bookingticket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long userId;          // ID của người dùng muốn đặt vé
    private Long scheduleId;      // ID của lịch trình xe buýt mà người dùng chọn
    private String seatId;        // ID ghế cụ thể (hoặc null nếu tự động chọn ghế)
    private String paymentMethod; // Phương thức thanh toán (ví dụ: "CreditCard", "Cash")
}
