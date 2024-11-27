package com.Loon;

import com.bookingticket.BookingTicketWebApplication;
import com.bookingticket.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringBootTest(classes = BookingTicketWebApplication.class)
@SpringJUnitWebConfig
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    public void testSendEmail() throws MessagingException {
        emailService.sendWelcomeEmail("kecuong71@gmail.com","Loon","lon");
    }
}
