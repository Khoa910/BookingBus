package com.bookingticket.Repository;

import com.bookingticket.entity.Payment;
import com.bookingticket.entity.Ticket;
import com.bookingticket.enumtype.PaymentMethod;
import com.bookingticket.enumtype.StatusPayment;
import com.bookingticket.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentTest {

    @Autowired
    private PaymentRepository paymentRepository;

    // Test Thêm (Add) Payment
    @Test
    public void testAddPayment() {
        // Tạo đối tượng Ticket giả (giả sử Ticket đã tồn tại trong cơ sở dữ liệu)
        Ticket ticket = new Ticket(); // Giả sử bạn đã có Ticket với ID hợp lệ
        ticket.setId(1L); // ID của Ticket

        // Tạo đối tượng Payment mới
        Payment newPayment = new Payment();
        newPayment.setTicket(ticket);
        newPayment.setPayment_method(PaymentMethod.BANKING.name());
        newPayment.setAmount(BigDecimal.valueOf(500000)); // Số tiền thanh toán là 500,000 VND
        newPayment.setStatus(StatusPayment.SUCCESS.name());

        // Lưu Payment vào cơ sở dữ liệu
        Payment savedPayment = paymentRepository.save(newPayment);

        // Kiểm tra xem Payment đã được lưu thành công chưa
        assertNotNull(savedPayment.getId(), "The saved Payment should have an ID");
        assertEquals("BANKING", savedPayment.getPayment_method(), "The payment method should match");
        assertEquals(BigDecimal.valueOf(500000), savedPayment.getAmount(), "The amount should match");
        assertEquals("SUCCESS", savedPayment.getStatus(), "The payment status should match");

        // Kiểm tra payment_time đã được tự động gán khi tạo
        assertNotNull(savedPayment.getPayment_time(), "The payment time should be automatically set");
        System.out.println("Saved Payment ID: " + savedPayment.getId());
        System.out.println("Saved Payment Method: " + savedPayment.getPayment_method());
        System.out.println("Saved Payment Amount: " + savedPayment.getAmount());
        System.out.println("Saved Payment Status: " + savedPayment.getStatus());
        System.out.println("Saved Payment Time: " + savedPayment.getPayment_time());
    }

    // Test Cập Nhật (Update) Payment
    @Test
    public void testUpdatePayment() {
        // Lấy đối tượng Payment đã lưu từ cơ sở dữ liệu (giả sử ID là 1)
        Payment paymentToUpdate = paymentRepository.findById(1L).orElseThrow();

        // Cập nhật thông tin của Payment
        paymentToUpdate.setPayment_method("PayPal");
        paymentToUpdate.setAmount(BigDecimal.valueOf(600000)); // Thay đổi số tiền
        paymentToUpdate.setStatus("Pending");

        // Lưu Payment đã cập nhật
        Payment updatedPayment = paymentRepository.save(paymentToUpdate);

        // Kiểm tra xem Payment đã được cập nhật thành công
        assertNotNull(updatedPayment.getId(), "The updated Payment should have an ID");
        assertEquals("PayPal", updatedPayment.getPayment_method(), "The payment method should be updated correctly");
        assertEquals(BigDecimal.valueOf(600000), updatedPayment.getAmount(), "The amount should be updated correctly");
        assertEquals("Pending", updatedPayment.getStatus(), "The payment status should be updated correctly");

        // In ra thông tin của Payment đã cập nhật
        System.out.println("Updated Payment ID: " + updatedPayment.getId());
        System.out.println("Updated Payment Method: " + updatedPayment.getPayment_method());
        System.out.println("Updated Payment Amount: " + updatedPayment.getAmount());
        System.out.println("Updated Payment Status: " + updatedPayment.getStatus());
    }

    // Test Xóa (Delete) Payment
    @Test
    public void testDeletePayment() {
        // Giả sử bạn có một ID đã tồn tại trong cơ sở dữ liệu (ID là 1)
        Long existingId = 1L;

        // Kiểm tra xem Payment với ID đó có tồn tại không
        assertTrue(paymentRepository.findById(existingId).isPresent(), "Payment with ID " + existingId + " should exist");

        // Xóa Payment bằng ID
        paymentRepository.deleteById(existingId);

        // Kiểm tra lại xem Payment đã bị xóa chưa
        assertFalse(paymentRepository.findById(existingId).isPresent(), "Payment with ID " + existingId + " should be deleted");

        System.out.println("Payment with ID " + existingId + " has been deleted.");
    }
}