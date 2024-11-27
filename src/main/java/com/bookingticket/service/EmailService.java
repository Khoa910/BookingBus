package com.bookingticket.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendWelcomeEmail(String toEmail, String name, String username) throws MessagingException {
        // Tạo context để truyền dữ liệu vào template
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("username", username);
        context.setVariable("email", toEmail);

        // Render template
        String emailContent = templateEngine.process("welcome-email-template", context);

        // Tạo và gửi email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ticketbooking571@gmail.com"); // Đặt địa chỉ gửi email
        helper.setTo(toEmail);
        helper.setSubject("Chào mừng đến với hệ thống của chúng tôi!");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
    public void sendNewPasswordEmail(String toEmail, String name, String newPassword) throws MessagingException {
        // Tạo context để truyền dữ liệu vào template
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("newPassword", newPassword);

        // Render template email
        String emailContent = templateEngine.process("reset-password-email-template", context);

        // Tạo email và gửi
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ticketbooking571@gmail.com"); // Đặt địa chỉ gửi email
        helper.setTo(toEmail);
        helper.setSubject("Mật khẩu mới của bạn");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
    public void sendBookingConfirmationEmail(String toEmail, String name, Long scheduleID, String seatIds, Double price, String departureTime, String address, String phone) throws MessagingException {
        // Tạo context để truyền dữ liệu vào template
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("scheduleID", scheduleID);
        context.setVariable("seatIds", seatIds);
        context.setVariable("price", price);
        context.setVariable("departureTime", departureTime);
        context.setVariable("address", address);
        context.setVariable("phone", phone);

        // Render template email
        String emailContent = templateEngine.process("booking-confirmation-email", context);

        // Tạo email và gửi
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ticketbooking571@gmail.com"); // Đặt địa chỉ gửi email
        helper.setTo(toEmail);
        helper.setSubject("Thông tin đặt vé xe của bạn");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}