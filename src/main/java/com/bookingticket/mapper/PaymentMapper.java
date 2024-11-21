package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    // Ánh xạ từ PaymentRequest sang Payment entity
    Payment toEntity(PaymentRequest paymentRequest);

    // Ánh xạ từ Payment entity sang PaymentRespond
//    @Mapping(source = "ticket.id", target = "ticket_id")
    PaymentRespond toRespond(Payment payment);
}
