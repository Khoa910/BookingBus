//package com.bookingticket.Repository;
//
//import com.bookingticket.entity.Payment;
//import com.bookingticket.entity.Ticket;
//import com.bookingticket.enumtype.PaymentMethod;
//import com.bookingticket.enumtype.StatusPayment;
//import com.bookingticket.repository.PaymentRepository;
//import com.bookingticket.repository.TicketRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class PaymentTest {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    // Test Thêm (Add) Payment
//    @Test
//    public void testAddPayment() {
//        // Lấy đối tượng Ticket từ cơ sở dữ liệu
//        Ticket ticket = ticketRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//        // Tạo đối tượng Payment mới
//        Payment newPayment = new Payment();
//        newPayment.setTicket(ticket);
//        newPayment.setPayment_method(PaymentMethod.BANKING.name());  // Use PaymentMethod enum
//        newPayment.setAmount(BigDecimal.valueOf(500000));  // Set payment amount
//        newPayment.setStatus(StatusPayment.SUCCESS.name());  // Use StatusPayment enum
//
//        // Lưu Payment vào cơ sở dữ liệu
//        Payment savedPayment = paymentRepository.save(newPayment);
//
//        // Kiểm tra xem Payment đã được lưu thành công chưa
//        assertNotNull(savedPayment.getId(), "The saved Payment should have an ID");
//        assertEquals(BigDecimal.valueOf(500000), savedPayment.getAmount(), "The amount should match");
//        assertNotNull(savedPayment.getPayment_time(), "The payment time should be automatically set");
//
//        // Check status
//        assertEquals(StatusPayment.SUCCESS.name(), savedPayment.getStatus(), "The payment status should match");
//
//        System.out.println("Saved Payment ID: " + savedPayment.getId());
//        System.out.println("Saved Payment Method: " + savedPayment.getPayment_method());
//        System.out.println("Saved Payment Amount: " + savedPayment.getAmount());
//        System.out.println("Saved Payment Status: " + savedPayment.getStatus());
//        System.out.println("Saved Payment Time: " + savedPayment.getPayment_time());
//    }
//
//    // Test Cập Nhật (Update) Payment
//    @Test
//    public void testUpdatePayment() {
//        // Lấy đối tượng Payment đã lưu từ cơ sở dữ liệu
//        Payment paymentToUpdate = paymentRepository.findById(1L).orElseThrow(() -> new RuntimeException("Payment not found"));
//
//        // Cập nhật thông tin của Payment
//        paymentToUpdate.setPayment_method(PaymentMethod.CASH.name()); // Change payment method
//        paymentToUpdate.setAmount(BigDecimal.valueOf(600000)); // Change amount
//        paymentToUpdate.setStatus(StatusPayment.PENDING.name()); // Change status
//
//        // Lưu Payment đã cập nhật
//        Payment updatedPayment = paymentRepository.save(paymentToUpdate);
//
//        // Kiểm tra xem Payment đã được cập nhật thành công
//        assertNotNull(updatedPayment.getId(), "The updated Payment should have an ID");
//        assertEquals(PaymentMethod.CASH.name(), updatedPayment.getPayment_method(), "The payment method should be updated correctly");
//        assertEquals(BigDecimal.valueOf(600000), updatedPayment.getAmount(), "The amount should be updated correctly");
//        assertEquals(StatusPayment.PENDING.name(), updatedPayment.getStatus(), "The payment status should be updated correctly");
//
//        // In ra thông tin của Payment đã cập nhật
//        System.out.println("Updated Payment ID: " + updatedPayment.getId());
//        System.out.println("Updated Payment Method: " + updatedPayment.getPayment_method());
//        System.out.println("Updated Payment Amount: " + updatedPayment.getAmount());
//        System.out.println("Updated Payment Status: " + updatedPayment.getStatus());
//    }
//
//    // Test Xóa (Delete) Payment
//    @Test
//    public void testDeletePayment() {
//        // Giả sử bạn có một ID đã tồn tại trong cơ sở dữ liệu (ID là 1)
//        Long existingId = 1L;
//
//        // Kiểm tra xem Payment với ID đó có tồn tại không
//        assertTrue(paymentRepository.findById(existingId).isPresent(), "Payment with ID " + existingId + " should exist");
//
//        // Xóa Payment bằng ID
//        paymentRepository.deleteById(existingId);
//
//        // Kiểm tra lại xem Payment đã bị xóa chưa
//        assertFalse(paymentRepository.findById(existingId).isPresent(), "Payment with ID " + existingId + " should be deleted");
//
//        System.out.println("Payment with ID " + existingId + " has been deleted.");
//    }
//}
