//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.PaymentRequest;
//import com.bookingticket.dto.respond.PaymentRespond;
//import com.bookingticket.entity.Payment;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface PaymentMapper {
//
//    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
//
//    // Ánh xạ từ PaymentRequest sang Payment entity
//    @Mapping(target = "ticket.id", source = "ticket_id")
//    @Mapping(target = "payment_method", source = "payment_method")
//    @Mapping(target = "status", source = "status")
//    Payment toEntity(PaymentRequest paymentRequest);
//
//    // Ánh xạ từ Payment entity sang PaymentRespond DTO
//    @Mapping(target = "ticket_id", source = "ticket")
//    PaymentRespond toRespond(Payment payment);
//}
