package com.bookingticket.Repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.BusStation;
import com.bookingticket.repository.BusScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BusScheduleTest {

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    // Test Thêm (Add) BusSchedule
    @Test
    public void testAddBusSchedule() {
        // Tạo các đối tượng Bus, BusStation (giả sử đã có các đối tượng này trong cơ sở dữ liệu)
        Bus bus = new Bus(); // Giả sử bạn đã tạo Bus và có ID hợp lệ
        bus.setId(1L); // ID của Bus mà bạn muốn kết nối

        BusStation departureStation = new BusStation();
        departureStation.setId(1L); // ID của DepartureStation

        BusStation arrivalStation = new BusStation();
        arrivalStation.setId(2L); // ID của ArrivalStation

        // Tạo đối tượng BusSchedule mới
        BusSchedule newSchedule = new BusSchedule();
        newSchedule.setBus(bus);
        newSchedule.setDepartureStation(departureStation);
        newSchedule.setArrivalStation(arrivalStation);
        newSchedule.setDeparture_time(LocalDateTime.of(2024, 11, 18, 9, 0)); // Giả sử giờ khởi hành là 9:00
        newSchedule.setArrival_time(LocalDateTime.of(2024, 11, 18, 12, 0)); // Giả sử giờ đến là 12:00
        newSchedule.setPrice(BigDecimal.valueOf(200000)); // Giá vé 200,000 VND

        // Lưu đối tượng vào cơ sở dữ liệu
        BusSchedule savedSchedule = busScheduleRepository.save(newSchedule);

        // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
        assertNotNull(savedSchedule.getId(), "The saved BusSchedule should have an ID");
        assertEquals(newSchedule.getDeparture_time(), savedSchedule.getDeparture_time(), "The departure time should be the same as the input");
        assertEquals(newSchedule.getArrival_time(), savedSchedule.getArrival_time(), "The arrival time should be the same as the input");
        assertEquals(newSchedule.getPrice(), savedSchedule.getPrice(), "The price should be the same as the input");

        // In ra thông tin của lịch trình đã lưu
        System.out.println("Saved BusSchedule ID: " + savedSchedule.getId());
        System.out.println("Saved BusSchedule Departure Time: " + savedSchedule.getDeparture_time());
        System.out.println("Saved BusSchedule Arrival Time: " + savedSchedule.getArrival_time());
        System.out.println("Saved BusSchedule Price: " + savedSchedule.getPrice());
    }

    // Test Cập Nhật (Update) BusSchedule
    @Test
    public void testUpdateBusSchedule() {
        // Lấy đối tượng BusSchedule đã lưu từ cơ sở dữ liệu (giả sử ID là 1)
        BusSchedule scheduleToUpdate = busScheduleRepository.findById(1L).orElseThrow();

        // Cập nhật thông tin của BusSchedule
        scheduleToUpdate.setDeparture_time(LocalDateTime.of(2024, 11, 18, 10, 0)); // Thay đổi giờ khởi hành
        scheduleToUpdate.setArrival_time(LocalDateTime.of(2024, 11, 18, 13, 0)); // Thay đổi giờ đến
        scheduleToUpdate.setPrice(BigDecimal.valueOf(250000)); // Thay đổi giá vé

        // Lưu đối tượng đã cập nhật
        BusSchedule updatedSchedule = busScheduleRepository.save(scheduleToUpdate);

        // Kiểm tra xem đối tượng đã được cập nhật thành công
        assertNotNull(updatedSchedule.getId(), "The updated BusSchedule should have an ID");
        assertEquals(LocalDateTime.of(2024, 11, 18, 10, 0), updatedSchedule.getDeparture_time(), "The departure time should be updated correctly");
        assertEquals(LocalDateTime.of(2024, 11, 18, 13, 0), updatedSchedule.getArrival_time(), "The arrival time should be updated correctly");
        assertEquals(BigDecimal.valueOf(250000), updatedSchedule.getPrice(), "The price should be updated correctly");

        // In ra thông tin của lịch trình đã cập nhật
        System.out.println("Updated BusSchedule ID: " + updatedSchedule.getId());
        System.out.println("Updated BusSchedule Departure Time: " + updatedSchedule.getDeparture_time());
        System.out.println("Updated BusSchedule Arrival Time: " + updatedSchedule.getArrival_time());
        System.out.println("Updated BusSchedule Price: " + updatedSchedule.getPrice());
    }

    // Test Xóa (Delete) BusSchedule
    @Test
    public void testDeleteBusScheduleById() {
        // Giả sử bạn có một id đã tồn tại trong cơ sở dữ liệu (ID là 1)
        Long existingId = 1L;

        // Kiểm tra xem đối tượng với ID đó có tồn tại không
        assertTrue(busScheduleRepository.findById(existingId).isPresent(), "BusSchedule with ID " + existingId + " should exist");

        // Xóa đối tượng BusSchedule bằng ID
        busScheduleRepository.deleteById(existingId);

        // Kiểm tra lại xem đối tượng đã bị xóa chưa
        assertFalse(busScheduleRepository.findById(existingId).isPresent(), "BusSchedule with ID " + existingId + " should be deleted");

        System.out.println("BusSchedule with ID " + existingId + " has been deleted.");
    }
}
