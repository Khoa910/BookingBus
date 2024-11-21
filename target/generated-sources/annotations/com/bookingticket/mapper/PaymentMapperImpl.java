package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T18:37:35+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toEntity(PaymentRequest paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Payment payment = new Payment();

        return payment;
    }

    @Override
    public PaymentRespond toRespond(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRespond paymentRespond = new PaymentRespond();

        return paymentRespond;
    }
}
