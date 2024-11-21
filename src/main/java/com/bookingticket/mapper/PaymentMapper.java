package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "ticket.id", source = "ticket_id")
    @Mapping(target = "payment_method", source = "payment_method")
    @Mapping(target = "status", source = "status")
    Payment toEntity(PaymentRequest paymentRequest);

    @Mapping(target = "ticket_id", source = "ticket.id")
    @Mapping(target = "payment_method", source = "payment_method")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "payment_time", source = "payment_time")
    @Mapping(target = "status", source = "status")
    PaymentRespond toRespond(Payment payment);
}
