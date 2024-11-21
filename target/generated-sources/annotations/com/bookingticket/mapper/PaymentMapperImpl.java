package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T19:26:41+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toEntity(PaymentRequest paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setAmount( paymentRequest.getAmount() );
        if ( paymentRequest.getPayment_method() != null ) {
            payment.setPayment_method( paymentRequest.getPayment_method().name() );
        }
        if ( paymentRequest.getStatus() != null ) {
            payment.setStatus( paymentRequest.getStatus().name() );
        }

        return payment;
    }

    @Override
    public PaymentRespond toRespond(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRespond paymentRespond = new PaymentRespond();

        paymentRespond.setAmount( payment.getAmount() );
        paymentRespond.setId( payment.getId() );
        paymentRespond.setPayment_method( payment.getPayment_method() );
        paymentRespond.setPayment_time( payment.getPayment_time() );
        paymentRespond.setStatus( payment.getStatus() );

        return paymentRespond;
    }
}
