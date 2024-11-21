package com.bookingticket.Repository;

import com.bookingticket.entity.BusStation;
import com.bookingticket.repository.BusStationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BusStationTest {

    @Autowired
    private BusStationRepository busStationRepository;

    // Test Thêm (Add) BusStation
    @Test
    public void testAddBusStation() {
        // Tạo đối tượng BusStation mới
        BusStation newStation = new BusStation();
        newStation.setName("Station A");
        newStation.setAddress("123 Main Street");

        // Lưu đối tượng vào cơ sở dữ liệu
        BusStation savedStation = busStationRepository.save(newStation);

        // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
        assertNotNull(savedStation.getId(), "The saved BusStation should have an ID");
        assertEquals(newStation.getName(), savedStation.getName(), "The name should be the same as the input");
        assertEquals(newStation.getAddress(), savedStation.getAddress(), "The address should be the same as the input");

        // In ra thông tin của trạm xe đã lưu
        System.out.println("Saved BusStation ID: " + savedStation.getId());
        System.out.println("Saved BusStation Name: " + savedStation.getName());
        System.out.println("Saved BusStation Address: " + savedStation.getAddress());
    }

    // Test Cập Nhật (Update) BusStation
    @Test
    public void testUpdateBusStation() {
        // Lấy đối tượng đã lưu từ cơ sở dữ liệu
        BusStation stationToUpdate = busStationRepository.findById(4L).orElseThrow();

        // Cập nhật thông tin trạm xe
        stationToUpdate.setName("Station B");
        stationToUpdate.setAddress("456 Another Street");

        // Lưu đối tượng đã cập nhật
        BusStation updatedStation = busStationRepository.save(stationToUpdate);

        // Kiểm tra xem đối tượng đã được cập nhật thành công
        assertNotNull(updatedStation.getId(), "The updated BusStation should have an ID");
        assertEquals("Station B", updatedStation.getName(), "The name should be updated correctly");
        assertEquals("456 Another Street", updatedStation.getAddress(), "The address should be updated correctly");

        // In ra thông tin trạm xe đã cập nhật
        System.out.println("Updated BusStation ID: " + updatedStation.getId());
        System.out.println("Updated BusStation Name: " + updatedStation.getName());
        System.out.println("Updated BusStation Address: " + updatedStation.getAddress());
    }

    // Test Xóa (Delete) BusStation
    @Test
    public void testDeleteBusStationById() {
        // Giả sử bạn có một id đã tồn tại trong cơ sở dữ liệu
        Long existingId = 1L; // Thay bằng ID thực tế có trong cơ sở dữ liệu của bạn

        // Kiểm tra xem đối tượng với ID đó có tồn tại không
        assertTrue(busStationRepository.findById(existingId).isPresent(), "BusStation with ID " + existingId + " should exist");

        // Xóa đối tượng BusStation bằng ID
        busStationRepository.deleteById(existingId);

        // Kiểm tra lại xem đối tượng đã bị xóa chưa
        assertFalse(busStationRepository.findById(existingId).isPresent(), "BusStation with ID " + existingId + " should be deleted");

        System.out.println("BusStation with ID " + existingId + " has been deleted.");
    }
}
