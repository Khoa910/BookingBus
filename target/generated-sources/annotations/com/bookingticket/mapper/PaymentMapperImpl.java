package com.bookingticket.mapper;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import com.bookingticket.entity.Ticket;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-27T23:57:01+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toEntity(PaymentRequest paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setTicket( paymentRequestToTicket( paymentRequest ) );
        if ( paymentRequest.getPayment_method() != null ) {
            payment.setPayment_method( paymentRequest.getPayment_method().name() );
        }
        if ( paymentRequest.getStatus() != null ) {
            payment.setStatus( paymentRequest.getStatus().name() );
        }
        payment.setAmount( paymentRequest.getAmount() );

        return payment;
    }

    @Override
    public PaymentRespond toRespond(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRespond paymentRespond = new PaymentRespond();

        paymentRespond.setTicket_id( paymentTicketId( payment ) );
        paymentRespond.setPayment_method( payment.getPayment_method() );
        paymentRespond.setAmount( payment.getAmount() );
        paymentRespond.setPayment_time( payment.getPayment_time() );
        paymentRespond.setStatus( payment.getStatus() );
        paymentRespond.setId( payment.getId() );

        return paymentRespond;
    }

    protected Ticket paymentRequestToTicket(PaymentRequest paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setId( paymentRequest.getTicket_id() );

        return ticket;
    }

    private Long paymentTicketId(Payment payment) {
        Ticket ticket = payment.getTicket();
        if ( ticket == null ) {
            return null;
        }
        return ticket.getId();
    }
}
