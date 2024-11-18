package com.bookingticket.Repository;

import com.bookingticket.entity.Bus;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.BusStation;
import com.bookingticket.enumtype.BusType;
import com.bookingticket.repository.BusCompanyRepository;
import com.bookingticket.repository.BusRepository;
import com.bookingticket.repository.BusStationRepository;
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
    private BusStationRepository busStationRepository;

    @Test
    public void testAddBus() {
        // Lấy đối tượng BusCompany và BusStation có sẵn trong cơ sở dữ liệu
        BusCompany busCompany = busCompanyRepository.findById(1L).get();


        BusStation departureStation = busStationRepository.findById(1L).get();


        BusStation arrivalStation = busStationRepository.findById(2L).get();


        // Tạo đối tượng Bus mới
        Bus newBus = new Bus();
        newBus.setLicense_plate("29A-12345");
        newBus.setSeat_count(30);
        newBus.setBus_type(BusType.SLEEPER.name());  // Dùng enum để thiết lập loại xe
        newBus.setBus_company(busCompany);  // Thiết lập công ty xe
        newBus.setDepartureStation(departureStation);  // Thiết lập bến đi
        newBus.setArrivalStation(arrivalStation);  // Thiết lập bến đến

        // Lưu đối tượng Bus vào cơ sở dữ liệu
        Bus savedBus = busRepository.save(newBus);

        // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
        assertNotNull(savedBus.getId(), "The saved Bus should have an ID");
        assertEquals(newBus.getLicense_plate(), savedBus.getLicense_plate(), "The license plate should be the same as the input");
        assertEquals(newBus.getSeat_count(), savedBus.getSeat_count(), "The seat count should be the same as the input");
        assertEquals(newBus.getBus_type(), savedBus.getBus_type(), "The bus type should be the same as the input");
        assertEquals(newBus.getBus_company().getId(), savedBus.getBus_company().getId(), "The bus company should match");
        assertEquals(newBus.getDepartureStation().getId(), savedBus.getDepartureStation().getId(), "The departure station should match");
        assertEquals(newBus.getArrivalStation().getId(), savedBus.getArrivalStation().getId(), "The arrival station should match");

        // In ra thông tin của bus đã lưu
        System.out.println("Saved Bus ID: " + savedBus.getId());
        System.out.println("Saved Bus License Plate: " + savedBus.getLicense_plate());
        System.out.println("Saved Bus Seat Count: " + savedBus.getSeat_count());
    }
}
