package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import com.bookingticket.entity.Ticket;
import com.bookingticket.enumtype.PaymentMethod;
import com.bookingticket.enumtype.StatusPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMapperTest {

    private PaymentMapper paymentMapper;

    @BeforeEach
    public void setUp() {
        paymentMapper = Mappers.getMapper(PaymentMapper.class); // Initialize the mapper
    }

    @Test
    public void testToEntity() {
        // Tạo PaymentRequest giả
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setTicket_id(1L);
        paymentRequest.setPayment_method(PaymentMethod.CREDIT);
        paymentRequest.setStatus(StatusPayment.PENDING);

        // Ánh xạ từ PaymentRequest sang Payment entity
        Payment payment = paymentMapper.toEntity(paymentRequest);

        // Kiểm tra các trường trong Payment entity
        assertEquals(paymentRequest.getTicket_id(), payment.getTicket().getId());
        assertEquals(paymentRequest.getPayment_method(), payment.getPayment_method());
        assertEquals(paymentRequest.getStatus(), payment.getStatus());
    }

    @Test
    public void testToRespond() {
        // Tạo Payment entity giả
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setPayment_method("Credit Card");
        payment.setStatus("Completed");
        BigDecimal amount = BigDecimal.valueOf(10000D);  // Chuyển từ double sang BigDecimal
        payment.setAmount(amount);
        payment.setPayment_time(LocalDateTime.now());

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        payment.setTicket(ticket);

        // Ánh xạ từ Payment entity sang PaymentRespond
        PaymentRespond paymentRespond = paymentMapper.toRespond(payment);

        // Kiểm tra các trường trong PaymentRespond
        assertEquals(payment.getTicket().getId(), paymentRespond.getTicket_id());
        assertEquals(payment.getPayment_method(), paymentRespond.getPayment_method());
        assertEquals(payment.getAmount(), paymentRespond.getAmount());
        assertEquals(payment.getPayment_time(), paymentRespond.getPayment_time());
        assertEquals(payment.getStatus(), paymentRespond.getStatus());
    }
}
