package com.bookingticket.Repository;

import com.bookingticket.entity.BusCompany;
import com.bookingticket.repository.BusCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BusCompanyTest {

    @Autowired
    private BusCompanyRepository busCompanyRepository;

    @Test
    public void testSaveBusCompany() {
        // Tạo một BusCompany mới
        BusCompany company = new BusCompany();
        company.setName("Example Bus Company");
        company.setPhone_number("123456789");

        // Lưu BusCompany vào cơ sở dữ liệu
        BusCompany savedCompany = busCompanyRepository.save(company);

        // Kiểm tra xem BusCompany đã được lưu thành công chưa
        assertNotNull(savedCompany, "The saved BusCompany should not be null");
        assertNotNull(savedCompany.getId(), "The saved BusCompany should have an ID");

        // In ra thông tin để debug
        System.out.println("Saved BusCompany ID: " + savedCompany.getId());
    }
}
