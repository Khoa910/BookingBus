package com.bookingticket.mapper;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestConfig.class)
public class BusCompanyMapperTest {

    @Autowired
    private BusCompanyMapper busCompanyMapper;

    @Test
    public void testToEntity() {
        // Tạo đối tượng BusCompanyRequest với các tham số cần thiết
        BusCompanyRequest request = new BusCompanyRequest("Phuong Trang", "0123456789");

        // Chuyển đổi BusCompanyRequest thành BusCompany entity
        BusCompany busCompany = busCompanyMapper.toEntity(request);

        // Kiểm tra các trường trong entity BusCompany đã được ánh xạ đúng
        assertEquals("Phuong Trang", busCompany.getName());  // Kiểm tra tên công ty
        assertEquals("0123456789", busCompany.getPhone_number());  // Kiểm tra số điện thoại
    }

    @Test
    public void testToRespond() {
        // Tạo đối tượng BusCompany entity với các tham số cần thiết
        BusCompany busCompany = new BusCompany();
        busCompany.setName("Phuong Trang");
        busCompany.setPhone_number("0123456789");

        // Chuyển đổi BusCompany entity thành BusCompanyRespond DTO
        BusCompanyRespond busCompanyRespond = busCompanyMapper.toRespond(busCompany);

        // Kiểm tra các trường trong BusCompanyRespond đã được ánh xạ đúng
        assertEquals("Phuong Trang", busCompanyRespond.getName());  // Kiểm tra tên công ty
        assertEquals("0123456789", busCompanyRespond.getPhone_number());  // Kiểm tra số điện thoại
    }
}
