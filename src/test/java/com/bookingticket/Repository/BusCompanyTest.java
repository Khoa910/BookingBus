package com.bookingticket.Repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookingticket.entity.BusCompany;
import com.bookingticket.repository.BusCompanyRepository;

@SpringBootTest
public class BusCompanyTest {

   @Autowired
   private BusCompanyRepository busCompanyRepository;

   @Test
   public void testSaveBusCompany() {
       BusCompany company = new BusCompany();
       company.setName("Test Company");
       company.setPhone_number("123456789");

       BusCompany savedCompany = busCompanyRepository.save(company);

       // Kiểm tra các trường hợp
       assertNotNull(savedCompany.getId());  // Kiểm tra ID được sinh ra
       assertEquals("Test Company", savedCompany.getName());  // Kiểm tra tên
       assertEquals("123456789", savedCompany.getPhone_number());  // Kiểm tra số điện thoại
   }
}
