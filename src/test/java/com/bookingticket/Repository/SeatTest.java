package com.bookingticket.Repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.Seat;
import com.bookingticket.repository.SeatRepository;
import com.bookingticket.repository.BusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeatTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;

    // Test Thêm (Add) Seat
    @Test
    public void testAddSeat() {
        // Giả sử bạn đã có đối tượng Bus tồn tại trong cơ sở dữ liệu
        Bus bus = busRepository.findById(1L).orElseThrow(() -> new RuntimeException("Bus not found"));

        // Tạo đối tượng Seat mới
        Seat newSeat = new Seat();
        newSeat.setId_seat("90-1");
        newSeat.setBus(bus);  // Gán Bus đã tồn tại
        newSeat.setStatus("AVAILABLE");
        newSeat.setSeat_name("A1");

        // Lưu Seat vào cơ sở dữ liệu
        Seat savedSeat = seatRepository.save(newSeat);

        // Kiểm tra xem Seat đã được lưu thành công chưa
        assertNotNull(savedSeat.getId_seat(), "The saved Seat should have an ID");
        assertEquals("A1", savedSeat.getId_seat(), "The seat ID should match");
        assertEquals("AVAILABLE", savedSeat.getStatus(), "The seat status should match");
        assertEquals("A1", savedSeat.getSeat_name(), "The seat name should match");
        assertNotNull(savedSeat.getBus(), "The bus should be associated with the seat");

        // In ra thông tin của Seat đã lưu
        System.out.println("Saved Seat ID: " + savedSeat.getId_seat());
        System.out.println("Saved Seat Status: " + savedSeat.getStatus());
        System.out.println("Saved Seat Name: " + savedSeat.getSeat_name());
    }

    // Test Cập Nhật (Update) Seat
    @Test
    public void testUpdateSeat() {
        // Lấy đối tượng Seat đã lưu từ cơ sở dữ liệu (giả sử ID là "A1")
        Seat seatToUpdate = seatRepository.findById("A1").orElseThrow(() -> new RuntimeException("Seat not found"));

        // Cập nhật thông tin của Seat
        seatToUpdate.setStatus("BOOKED");

        // Lưu Seat đã cập nhật
        Seat updatedSeat = seatRepository.save(seatToUpdate);

        // Kiểm tra xem Seat đã được cập nhật thành công
        assertNotNull(updatedSeat.getId_seat(), "The updated Seat should have an ID");
        assertEquals("BOOKED", updatedSeat.getStatus(), "The seat status should be updated correctly");

        // In ra thông tin của Seat đã cập nhật
        System.out.println("Updated Seat ID: " + updatedSeat.getId_seat());
        System.out.println("Updated Seat Status: " + updatedSeat.getStatus());
        System.out.println("Updated Seat Name: " + updatedSeat.getSeat_name());
    }

    // Test Xóa (Delete) Seat
    @Test
    public void testDeleteSeat() {
        // Giả sử bạn có một ID đã tồn tại trong cơ sở dữ liệu (ID là "A1")
        String existingId = "A1";

        // Kiểm tra xem Seat với ID đó có tồn tại không
        assertTrue(seatRepository.findById(existingId).isPresent(), "Seat with ID " + existingId + " should exist");

        // Xóa Seat bằng ID
        seatRepository.deleteById(existingId);

        // Kiểm tra lại xem Seat đã bị xóa chưa
        assertFalse(seatRepository.findById(existingId).isPresent(), "Seat with ID " + existingId + " should be deleted");

        System.out.println("Seat with ID " + existingId + " has been deleted.");
    }
}
