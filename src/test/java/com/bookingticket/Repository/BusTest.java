package com.bookingticket.Repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.SeatType;
import com.bookingticket.entity.BusStation;
import com.bookingticket.enumtype.BusType;
import com.bookingticket.repository.BusRepository;
import com.bookingticket.repository.BusCompanyRepository;
import com.bookingticket.repository.SeatTypeRepository;
import com.bookingticket.repository.BusStationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BusTest {

   @Autowired
   private BusRepository busRepository;

   @Autowired
   private BusCompanyRepository busCompanyRepository;

   @Autowired
   private SeatTypeRepository seatTypeRepository;

   @Autowired
   private BusStationRepository busStationRepository;

   @Test
   public void testAddBus() {

       // Lấy đối tượng BusCompany, SeatType và BusStation từ cơ sở dữ liệu
       BusCompany busCompany = busCompanyRepository.findById(1L).get();
       SeatType seatType = seatTypeRepository.findById(1L).get();
       BusStation departureStation = busStationRepository.findById(1L).get();
       BusStation arrivalStation = busStationRepository.findById(2L).get();

       // Tạo đối tượng Bus mới
       Bus newBus = new Bus();
       newBus.setLicense_plate("29A-12345");
       newBus.setSeatType(seatType);
       newBus.setBus_type(BusType.SLEEPER.name());
       newBus.setBus_company(busCompany);
       newBus.setDepartureStation(departureStation);
       newBus.setArrivalStation(arrivalStation);

       // Lưu đối tượng vào cơ sở dữ liệu
       Bus savedBus = busRepository.save(newBus);

       // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
       assertNotNull(savedBus.getId(), "The saved Bus should have an ID");
       assertEquals(newBus.getLicense_plate(), savedBus.getLicense_plate(), "The license plate should be the same as the input");
       assertEquals(newBus.getBus_type(), savedBus.getBus_type(), "The bus type should be the same as the input");

       // In ra thông tin của xe đã lưu
       System.out.println("Saved Bus ID: " + savedBus.getId());
       System.out.println("Saved Bus License Plate: " + savedBus.getLicense_plate());
       System.out.println("Saved Bus Type: " + savedBus.getBus_type());
       System.out.println("Saved Bus Company: " + savedBus.getBus_company().getName());
       System.out.println("Saved Departure Station: " + savedBus.getDepartureStation().getName());
       System.out.println("Saved Arrival Station: " + savedBus.getArrivalStation().getName());
   }

   @Test
   public void testUpdateBus() {

       // Lấy đối tượng Bus đã lưu từ cơ sở dữ liệu
       Bus busToUpdate = busRepository.findById(1L).orElseThrow();

       // Cập nhật thông tin xe
       busToUpdate.setLicense_plate("29A-54321");
       busToUpdate.setBus_type("Ghế ngồi");

       // Lưu đối tượng đã cập nhật
       Bus updatedBus = busRepository.save(busToUpdate);

       // Kiểm tra xem đối tượng đã được cập nhật thành công
       assertNotNull(updatedBus.getId(), "The updated Bus should have an ID");
       assertEquals("29A-54321", updatedBus.getLicense_plate(), "The license plate should be updated correctly");
       assertEquals("Ghế ngồi", updatedBus.getBus_type(), "The bus type should be updated correctly");

       // In ra thông tin của xe đã cập nhật
       System.out.println("Updated Bus ID: " + updatedBus.getId());
       System.out.println("Updated Bus License Plate: " + updatedBus.getLicense_plate());
       System.out.println("Updated Bus Type: " + updatedBus.getBus_type());
   }

   @Test
   public void testDeleteBusById() {
       // Giả sử bạn có một id đã tồn tại trong cơ sở dữ liệu
       Long existingId = 1L; // Thay bằng ID thực tế có trong cơ sở dữ liệu của bạn

       // Kiểm tra xem đối tượng với ID đó có tồn tại không
       assertTrue(busRepository.findById(existingId).isPresent(), "Bus with ID " + existingId + " should exist");

       // Xóa đối tượng Bus bằng ID
       busRepository.deleteById(existingId);

       // Kiểm tra lại xem đối tượng đã bị xóa chưa
       assertFalse(busRepository.findById(existingId).isPresent(), "Bus with ID " + existingId + " should be deleted");

       System.out.println("Bus with ID " + existingId + " has been deleted.");
   }

    @Test
    @Transactional
    public void testFindAllBuses() {
        // Lấy tất cả các đối tượng Bus từ cơ sở dữ liệu
        Iterable<Bus> buses = busRepository.findAll();

        // Kiểm tra xem danh sách các xe có rỗng không
        assertNotNull(buses, "The list of buses should not be null");
        assertTrue(buses.iterator().hasNext(), "The list of buses should not be empty");

        // In ra thông tin của tất cả các xe đã tìm thấy
        System.out.println("List of all buses:");
        buses.forEach(bus -> {
            System.out.println("Bus ID: " + bus.getId());
            System.out.println("Bus License Plate: " + bus.getLicense_plate());
            System.out.println("Bus Type: " + bus.getBus_type());
            // Các quan hệ lazy-loaded sẽ không gặp vấn đề khi session vẫn mở
            System.out.println("Bus Company: " + bus.getBus_company().getName());
            System.out.println("Departure Station: " + bus.getDepartureStation().getName());
            System.out.println("Arrival Station: " + bus.getArrivalStation().getName());
        });
    }
}
