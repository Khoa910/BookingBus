package com.bookingticket.Repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.Ticket;
import com.bookingticket.entity.User;
import com.bookingticket.enumtype.TicketStatus;
import com.bookingticket.repository.BusRepository;
import com.bookingticket.repository.TicketRepository;
import com.bookingticket.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketTest {

   @Autowired
   private TicketRepository ticketRepository;

   @Autowired
   private BusRepository busRepository;

   @Autowired
   private UserRepository userRepository;

   @Test
   public void testAddTicket() {
       // Lấy đối tượng Bus và User có sẵn trong cơ sở dữ liệu
       Bus bus = busRepository.findById(1L).get();  // Bus với ID = 1
       User user = userRepository.findById(1L).get();  // User với ID = 1

       // Tạo đối tượng Ticket mới
       Ticket newTicket = new Ticket();
       newTicket.setBus(bus);  // Thiết lập Bus cho vé
       newTicket.setUser(user);  // Thiết lập User cho vé
       newTicket.setSeat_number("A1");  // Số ghế
       newTicket.setDeparture_time(LocalDateTime.of(2024, 11, 18, 10, 0));  // Thời gian khởi hành
       newTicket.setPrice(BigDecimal.valueOf(250000));  // Giá vé
       newTicket.setStatus(TicketStatus.CANCELLED.name());  // Trạng thái vé

       // Lưu đối tượng Ticket vào cơ sở dữ liệu
       Ticket savedTicket = ticketRepository.save(newTicket);

       // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
       assertNotNull(savedTicket.getId(), "The saved Ticket should have an ID");
       assertEquals(newTicket.getSeat_number(), savedTicket.getSeat_number(), "The seat number should match");
       assertEquals(newTicket.getDeparture_time(), savedTicket.getDeparture_time(), "The departure time should match");
       assertEquals(newTicket.getPrice(), savedTicket.getPrice(), "The price should match");
       assertEquals(newTicket.getStatus(), savedTicket.getStatus(), "The status should match");

       // Kiểm tra mối quan hệ giữa Ticket, Bus và User
       assertEquals(newTicket.getBus().getId(), savedTicket.getBus().getId(), "The bus ID should match");
       assertEquals(newTicket.getUser().getId(), savedTicket.getUser().getId(), "The user ID should match");

       // In ra thông tin của ticket đã lưu
       System.out.println("Saved Ticket ID: " + savedTicket.getId());
       System.out.println("Saved Ticket Seat Number: " + savedTicket.getSeat_number());
       System.out.println("Saved Ticket Departure Time: " + savedTicket.getDeparture_time());
       System.out.println("Saved Ticket Price: " + savedTicket.getPrice());
       System.out.println("Saved Ticket Status: " + savedTicket.getStatus());
   }

   @Test
   public void testUpdateTicket() {
       // Lấy đối tượng Ticket đã lưu từ cơ sở dữ liệu (giả sử ID là 1)
       Ticket ticketToUpdate = ticketRepository.findById(1L).orElseThrow();

       // Cập nhật thông tin của Ticket
       ticketToUpdate.setSeat_number("B1");
       ticketToUpdate.setDeparture_time(LocalDateTime.of(2024, 11, 18, 12, 0));  // Cập nhật thời gian khởi hành
       ticketToUpdate.setPrice(BigDecimal.valueOf(300000));  // Cập nhật giá vé
       ticketToUpdate.setStatus("Confirmed");  // Cập nhật trạng thái vé

       // Lưu Ticket đã cập nhật
       Ticket updatedTicket = ticketRepository.save(ticketToUpdate);

       // Kiểm tra xem Ticket đã được cập nhật thành công
       assertNotNull(updatedTicket.getId(), "The updated Ticket should have an ID");
       assertEquals("B1", updatedTicket.getSeat_number(), "The seat number should be updated correctly");
       assertEquals(LocalDateTime.of(2024, 11, 18, 12, 0), updatedTicket.getDeparture_time(), "The departure time should be updated correctly");
       assertEquals(BigDecimal.valueOf(300000), updatedTicket.getPrice(), "The price should be updated correctly");
       assertEquals("Confirmed", updatedTicket.getStatus(), "The status should be updated correctly");

       // In ra thông tin của Ticket đã cập nhật
       System.out.println("Updated Ticket ID: " + updatedTicket.getId());
       System.out.println("Updated Ticket Seat Number: " + updatedTicket.getSeat_number());
       System.out.println("Updated Ticket Departure Time: " + updatedTicket.getDeparture_time());
       System.out.println("Updated Ticket Price: " + updatedTicket.getPrice());
       System.out.println("Updated Ticket Status: " + updatedTicket.getStatus());
   }

   @Test
   public void testDeleteTicket() {
       // Giả sử bạn có một ID đã tồn tại trong cơ sở dữ liệu (ID là 1)
       Long existingId = 1L;

       // Kiểm tra xem Ticket với ID đó có tồn tại không
       assertTrue(ticketRepository.findById(existingId).isPresent(), "Ticket with ID " + existingId + " should exist");

       // Xóa Ticket bằng ID
       ticketRepository.deleteById(existingId);

       // Kiểm tra lại xem Ticket đã bị xóa chưa
       assertFalse(ticketRepository.findById(existingId).isPresent(), "Ticket with ID " + existingId + " should be deleted");

       System.out.println("Ticket with ID " + existingId + " has been deleted.");
   }
}
