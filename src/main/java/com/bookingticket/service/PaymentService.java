package com.bookingticket.service;

import com.bookingticket.dto.request.PaymentRequest;
import com.bookingticket.dto.respond.PaymentRespond;
import com.bookingticket.entity.Payment;
import com.bookingticket.mapper.PaymentMapper;
import com.bookingticket.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public List<PaymentRespond> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentMapper::toRespond)
                .collect(Collectors.toList());
    }

    public PaymentRespond getPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        return paymentOptional.map(paymentMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public PaymentRespond createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toRespond(savedPayment);
    }

    public PaymentRespond updatePayment(Long id, PaymentRequest request) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPayment_method(request.getPayment_method().name());
            payment.setAmount(request.getAmount());
            payment.setStatus(request.getStatus().name());
            Payment updatedPayment = paymentRepository.save(payment);
            return paymentMapper.toRespond(updatedPayment);
        } else {
            throw new RuntimeException("Payment not found with id: " + id);
        }
    }

    public void deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            paymentRepository.delete(paymentOptional.get());
        } else {
            throw new RuntimeException("Payment not found with id: " + id);
        }
    }
}
